package palaster.bb.entities.knowledge;

import java.util.ArrayList;
import java.util.List;

import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.entities.knowledge.pieces.KPBloodHive;
import palaster.bb.entities.knowledge.pieces.KPBoilingBlood;
import palaster.bb.entities.knowledge.pieces.KPBoundArmor;
import palaster.bb.entities.knowledge.pieces.KPCurseBlackTongue;
import palaster.bb.entities.knowledge.pieces.KPCurseUnblinkingEye;
import palaster.bb.entities.knowledge.pieces.KPDarkWings;
import palaster.bb.entities.knowledge.pieces.KPPossession;
import palaster.bb.entities.knowledge.pieces.KPSummonDemonicBankTeller;

public class BBKnowledge {

    private static List<IKnowledgePiece> knowledge = new ArrayList<IKnowledgePiece>();

    public static void addKnowledgePiece(IKnowledgePiece kp) {
        if(kp != null) {
            if(knowledge.isEmpty()) {
                knowledge.add(kp);
                return;
            } else
                for(IKnowledgePiece tkp : knowledge)
                    if(tkp.getName().equals(kp.getName())) {
                        System.out.println(tkp.getName() + " has already been registered.");
                        return;
                    }
            knowledge.add(kp);
        }
    }

    public static IKnowledgePiece getKnowledgePiece(int id) { return knowledge.get(id); }

    public static int getKnowledgePieceID(IKnowledgePiece kp) {
        for(int i = 0; i < knowledge.size(); i++)
            if(knowledge.get(i).getName().equals(kp.getName()))
                return i;
        return -1;
    }

    public static int getKnowledgeSize() { return knowledge.size(); }

    static {
        addKnowledgePiece(new KPBoilingBlood());
        addKnowledgePiece(new KPBoundArmor());
        addKnowledgePiece(new KPBloodHive());	
        addKnowledgePiece(new KPPossession());
        addKnowledgePiece(new KPCurseUnblinkingEye());
        addKnowledgePiece(new KPCurseBlackTongue());
        addKnowledgePiece(new KPDarkWings());
        addKnowledgePiece(new KPSummonDemonicBankTeller());
    }
}
