package palaster.bb.entities.knowledge;

import palaster.bb.api.capabilities.items.IKnowledgePiece;
import palaster.bb.entities.knowledge.pieces.KPBloodLink;
import palaster.bb.entities.knowledge.pieces.KPBoilingBlood;
import palaster.bb.entities.knowledge.pieces.KPBoundArmor;
import palaster.bb.entities.knowledge.pieces.KPEtherealChest;

import java.util.ArrayList;
import java.util.List;

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
        addKnowledgePiece(new KPBloodLink());
        addKnowledgePiece(new KPBoundArmor());
        addKnowledgePiece(new KPEtherealChest());
    }
}
