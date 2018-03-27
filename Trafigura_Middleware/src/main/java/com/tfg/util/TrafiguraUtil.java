package com.tfg.util;

import com.tfg.DO.LCDetailsDO;
import com.trafigura.model.LCDetails;
import net.corda.client.rpc.CordaRPCClient;
import net.corda.core.messaging.CordaRPCOps;
import net.corda.core.utilities.NetworkHostAndPort;

public class TrafiguraUtil {

    public static CordaRPCOps getRPCServiceByNode(String host, int port) {
        NetworkHostAndPort networkHostAndPort = new NetworkHostAndPort(host, port);
        final CordaRPCClient client = new CordaRPCClient(networkHostAndPort);
        return client.start("user1", "test").getProxy();
    }

    public static LCDetails prepareLCDetails(LCDetailsDO lcDetailDO) {
        return new LCDetails(lcDetailDO.getLc_id(),
                lcDetailDO.getLc_version(),
                lcDetailDO.getLcStatus(),
                lcDetailDO.getLcDate(),
                lcDetailDO.getGroupCompany(),
                lcDetailDO.getGcBusinessLine(),
                lcDetailDO.getCounterParty(),
                lcDetailDO.getCpBusinessLine(),
                lcDetailDO.getCommodity(),
                lcDetailDO.getQuantity(),
                lcDetailDO.getConractualLocation(),
                lcDetailDO.getIncoterms(),
                lcDetailDO.getMaterialSpecification(),
                lcDetailDO.getValueDate(),
                lcDetailDO.getPrice(),
                lcDetailDO.getAmount(),
                lcDetailDO.getPlaceOfPresentation(),
                lcDetailDO.getPlaceOfExpiry(),
                lcDetailDO.getDateOfIssue(),
                lcDetailDO.getDateOfExpiry(),
                lcDetailDO.getComments(),
                lcDetailDO.getUpdateTimestamp(),
                lcDetailDO.getDocSecureHashString(),
                lcDetailDO.getIssuingBank(),
                lcDetailDO.getIssuingBankAddress(),
                lcDetailDO.getAdvisingBank(),
                lcDetailDO.getAdvisingBankAddress(),
                lcDetailDO.getGroupCompanyAddress(),
                lcDetailDO.getCounterPartyAddress(),
                lcDetailDO.getTenorDays(),
                lcDetailDO.getTenorBasisCondition(),
                lcDetailDO.getTenorDate(),
                lcDetailDO.getTenorBasisEvent()
        );


    }
}
