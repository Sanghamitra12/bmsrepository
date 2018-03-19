package com.trafigura.flow

import co.paralleluniverse.fibers.Suspendable
import com.trafigura.stateandcontracts.LCDetails
import com.trafigura.stateandcontracts.LCContract
import com.trafigura.stateandcontracts.LCState
import com.trafigura.stateandcontracts.LC_CONTRACT_ID


import net.corda.core.contracts.Command
import net.corda.core.contracts.StateAndContract
import net.corda.core.flows.*
import net.corda.core.identity.Party
import net.corda.core.transactions.TransactionBuilder
import net.corda.core.utilities.ProgressTracker
import net.corda.core.contracts.requireThat
import net.corda.core.node.services.Vault
import net.corda.core.node.services.queryBy
import net.corda.core.node.services.vault.QueryCriteria
import net.corda.core.transactions.SignedTransaction



object LCRequestFlow {

    @InitiatingFlow
    @StartableByRPC
    class Initiator(private val lcState: LCState, private val listOfParticipants:MutableList<Party>) : FlowLogic<SignedTransaction>() {

        /**
         * The progress tracker checkpoints each stage of the flow and outputs the specified messages when each
         * checkpoint is reached in the code. See the 'progressTracker.currentStep' expressions within the call() function.
         */
        companion object {
            object GENERATING_TRANSACTION : ProgressTracker.Step("Generating transaction.")
            object VERIFYING_TRANSACTION : ProgressTracker.Step("Verifying contract constraints.")
            object SIGNING_TRANSACTION : ProgressTracker.Step("Signing transaction with our private key.")
            object GATHERING_SIGS : ProgressTracker.Step("Gathering the counterparty's signature.")
            {
                override fun childProgressTracker() = CollectSignaturesFlow.tracker()
            }

            object FINALISING_TRANSACTION : ProgressTracker.Step("Obtaining notary signature and recording transaction.") {
                override fun childProgressTracker() = FinalityFlow.tracker()
            }

            fun tracker() = ProgressTracker(
                    GENERATING_TRANSACTION,
                    VERIFYING_TRANSACTION,
                    SIGNING_TRANSACTION,
                    GATHERING_SIGS,
                    FINALISING_TRANSACTION
            )
        }
        override val progressTracker = tracker()
        /** The flow logic is encapsulated within the call() method. */
        @Suspendable
        override fun call(): SignedTransaction {
            println("inside flow")
            // We retrieve the notary identity from the network map.
            val notary = serviceHub.networkMapCache.notaryIdentities[0]

            // Stage 1.
            progressTracker.currentStep = GENERATING_TRANSACTION
            val outputContractAndState = StateAndContract(lcState, LC_CONTRACT_ID)
            val txCommand = Command(LCContract.Create(), listOfParticipants.toSet().map { it.owningKey })
            val txBuilder = TransactionBuilder(notary).withItems(outputContractAndState,txCommand)
            txBuilder.addAttachment(lcState.lcDetails.docSecureHash)

            // Stage 2. Verifying the transaction.
            progressTracker.currentStep = VERIFYING_TRANSACTION
            txBuilder.verify(serviceHub)

            // Stage 3. Sign the transaction
            progressTracker.currentStep = SIGNING_TRANSACTION
            val signedTx = serviceHub.signInitialTransaction(txBuilder)

            // Stage 4. Creating a session with the other party and obtaining the counterParty's signature.
            // Send the state to the counterParty, and receive it back with their signature.
            progressTracker.currentStep = GATHERING_SIGS
            val flowSessionList = mutableListOf<FlowSession>()
            for(participant in listOfParticipants){
                flowSessionList.add(initiateFlow(participant))
            }
            val fullySignedTx = subFlow(CollectSignaturesFlow(signedTx, flowSessionList, GATHERING_SIGS.childProgressTracker()))

            // Stage 5.
            progressTracker.currentStep = FINALISING_TRANSACTION
            // val applicationDao = ApplicationDao()

            // Finalising the transaction. Notarise and record the transaction in both parties' vaults.
            return subFlow(FinalityFlow(fullySignedTx, FINALISING_TRANSACTION.childProgressTracker()))
        }
    }

    @InitiatedBy(Initiator::class)
    class Acceptor(val otherPartySession: FlowSession) : FlowLogic<Unit>() {
        @Suspendable
        override fun call() {
            val signTransactionFlow = object : SignTransactionFlow(otherPartySession, SignTransactionFlow.tracker()) {
                override fun checkTransaction(stx: SignedTransaction) = requireThat {
                    val output = stx.tx.outputs.single().data
                    "Output state should be LCState." using (output is LCState)
                }
            }
            subFlow(signTransactionFlow)
        }
    }
}

@StartableByRPC
class detailDataDisplay() : FlowLogic<String>() {
    override fun call(): String {
        val qryCriteriaUnconsumed = QueryCriteria.VaultQueryCriteria(Vault.StateStatus.UNCONSUMED)
        val lcStateref = serviceHub.vaultService.queryBy<LCState>(qryCriteriaUnconsumed).states.single()
        return  lcStateref.state.data.lcDetails.toString();
    }
}