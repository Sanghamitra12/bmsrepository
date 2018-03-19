package com.trafigura.stateandcontracts

import net.corda.core.contracts.*
import net.corda.core.crypto.SecureHash
import net.corda.core.transactions.LedgerTransaction
import net.corda.core.identity.Party
import net.corda.core.schemas.MappedSchema
import net.corda.core.schemas.PersistentState
import net.corda.core.schemas.QueryableState
import net.corda.core.serialization.CordaSerializable
import javax.persistence.*


// *****************
// * Contract Code *
// *****************
// This is used to identify our contract when building a transaction
const val LC_CONTRACT_ID = "com.trafigura.stateandcontracts.LCContract"

class LCContract : Contract {
    // Our Create command.
    class Create : CommandData

    override fun verify(tx: LedgerTransaction) {
        val command = tx.commands.requireSingleCommand<Create>()

        requireThat {
            // Constraints on the shape of the transaction.
            "No inputs should be consumed when issuing an IOU." using (tx.inputs.isEmpty())
            "There should be one output state of type IOUState." using (tx.outputs.size == 1)

            // IOUspecific constraints.
            val out = tx.outputsOfType<LCState>().single()

            "The Sender and the receiver cannot be the same entity." using(out.participants[0] != out.participants[1])

            // Constraints on the signers.
            "There must be two signers." using (command.signers.toSet().size >= 2)
            //"The borrower and lender must be signers." using (command.signers.containsAll(out.listOfParticipants))
        }
    }
}

// *********
// * State *
// *********
data class LCState(val lcDetails: LCDetails, val listOfParticipants:MutableList<Party>) : QueryableState
{
    override val participants get() = listOfParticipants

    override fun supportedSchemas(): Iterable<MappedSchema> = listOf(LCSchema)

    override fun generateMappedObject(schema: MappedSchema): PersistentState {

        return when (schema) {
            is LCSchema -> LCSchema.LCDetail(
                    lc_id = this.lcDetails.lc_id,
                    name = this.lcDetails.name.toString()
            )
            else -> throw IllegalArgumentException("Unrecognised schema $schema")
        }
    }

    object LCSchema : MappedSchema(LCState::class.java, 1, listOf(LCDetail::class.java)) {

        @Entity
        @Table(name = "LC_DETAIL")
        class LCDetail(
                @Column(name = "lc_id")
                var lc_id: String?,

                @Column(name = "name")
                var name: String?

        ) : PersistentState()

    }

}

@CordaSerializable
data class LCDetails
(
        var lc_id: String?,
        var lc_: String?,
        var lcStatus: String?,
        var comments: String?,
        var docSecureHash: SecureHash


) {
    override fun equals(other: Any?): Boolean {
        return super.equals(other)
    }

    override fun hashCode(): Int {
        return super.hashCode()
    }
}