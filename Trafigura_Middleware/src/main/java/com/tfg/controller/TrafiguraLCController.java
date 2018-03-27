package com.tfg.controller;

import com.tfg.DO.LCDetailsDO;
import com.tfg.DO.UserDetails;
import com.tfg.util.TrafiguraUtil;
import com.trafigura.DTO.LCDetailsDTO;
import com.trafigura.DTO.UserDetailsDTO;
import com.trafigura.dao.*;
import com.trafigura.flow.TrafiguraLCFlow;
import com.trafigura.stateandcontracts.LCState;
import net.corda.core.identity.CordaX500Name;
import net.corda.core.identity.Party;
import net.corda.core.messaging.CordaRPCOps;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

@RestController("/trafigura")
public class TrafiguraLCController {

    public static final String CORDA_APP_HOST = "localhost";
    private static final String TFG = "TFG";
    private static final String CP = "CP";
    private static final String IB = "IB";

    @GetMapping("/login")
    private String getLoginPage() {
        return "downloadCSV";
    }

    @PostMapping("/userDetails")
    private @ResponseBody
    UserDetailsDTO getUserDetails(@RequestBody UserDetails userdetails) throws ExecutionException, InterruptedException {
        return getUserDetailsDTO(userdetails);
    }

    private UserDetailsDTO getUserDetailsDTO(@RequestBody UserDetails userdetails) throws InterruptedException, ExecutionException {
        CordaRPCOps cOps = TrafiguraUtil.getRPCServiceByNode(CORDA_APP_HOST, ApplicationDao.Companion.getNotaryRPCPort());
        UserDetailsDTO userdetailsDTO = new UserDetailsDTO(userdetails.getId(), userdetails.getUser_id(), userdetails.getUser_type(), userdetails.getPassword(), false);
        userdetailsDTO = cOps.startFlowDynamic(TrafiguraLCFlow.CheckUserExists.class, userdetailsDTO).
                getReturnValue().get();
        return userdetailsDTO;
    }

    @PostMapping("/LCAsDraft")
    private String createLCAsDraft(@RequestBody LCDetailsDO lcDetails) throws ExecutionException, InterruptedException {
        CordaRPCOps cOps = TrafiguraUtil.getRPCServiceByNode(CORDA_APP_HOST, ApplicationDao.Companion.getTrafiguraRPCPort());
        List<Party> partyList = new ArrayList<>();
        partyList.add(cOps.wellKnownPartyFromX500Name(CordaX500Name.parse(ApplicationDao.Companion.getTrafiguraX500Name())));
        LCState lcState = new LCState(TrafiguraUtil.prepareLCDetails(lcDetails), partyList);
        return cOps.startFlowDynamic(TrafiguraLCFlow.CreateLCAsDraft.class, lcState).
                getReturnValue().get();

    }

    @PostMapping("/LCStateVault")
    private @ResponseBody
    List<LCState> queryVaultForLCState(@RequestBody UserDetails userDetails) throws ExecutionException, InterruptedException {
        CordaRPCOps cOps = TrafiguraUtil.getRPCServiceByNode(CORDA_APP_HOST, ApplicationDao.Companion.getTrafiguraRPCPort());
        return cOps.startFlowDynamic(TrafiguraLCFlow.QueryVaultForLCState.class).getReturnValue().get();
    }

    @PostMapping("/LCDetailsList")
    private @ResponseBody
    List<LCDetailsDTO> fetchLCDetailsList(@RequestBody UserDetails userDetails) throws Exception {
        CordaRPCOps cOps = TrafiguraUtil.getRPCServiceByNode(CORDA_APP_HOST, ApplicationDao.Companion.getNotaryRPCPort());
        Party party = getParty(cOps, userDetails);
        if (party == null) {
            throw new Exception("User does not exists");
        }
        return cOps.startFlowDynamic(TrafiguraLCFlow.FetchLCDetailsList.class, party).getReturnValue().get();

    }

    private Party getParty(CordaRPCOps cOps, UserDetails userDetails) {
        Party party = null;
        try {
            UserDetailsDTO userDetailsDTO = getUserDetailsDTO(userDetails);
            if (TFG.equals(userDetailsDTO.getUser_type())) {
                party = cOps.wellKnownPartyFromX500Name(CordaX500Name.parse(ApplicationDao.Companion.getTrafiguraX500Name()));
            } else if (CP.equals(userDetailsDTO.getUser_type())) {
                party = cOps.wellKnownPartyFromX500Name(CordaX500Name.parse(ApplicationDao.Companion.getCPX500Name()));
            } else if (IB.equals(userDetailsDTO.getUser_type())) {
                party = cOps.wellKnownPartyFromX500Name(CordaX500Name.parse(ApplicationDao.Companion.getIssuingBankX500Name()));
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return party;
    }


}
