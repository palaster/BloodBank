package palaster.bb.entities.knowledge;

import palaster.bb.entities.knowledge.pieces.KPBloodLink;
import palaster.bb.entities.knowledge.pieces.KPBoilingBlood;

import java.util.ArrayList;
import java.util.List;

public class BBKnowledge {

    private static List<BBKnowledgePiece> knowledge = new ArrayList<BBKnowledgePiece>();

    public static void addKnowledgePiece(BBKnowledgePiece kp) {
        if(kp != null) {
            if(knowledge.isEmpty()) {
                knowledge.add(kp);
                return;
            } else
                for(BBKnowledgePiece tkp : knowledge)
                    if(tkp.getName().equals(kp.getName())) {
                        System.out.println(tkp.getName() + " has already been registered.");
                        return;
                    }
            knowledge.add(kp);
        }
    }

    public static BBKnowledgePiece getKnowledgePiece(int id) { return knowledge.get(id); }

    public static int getKnowledgePieceID(BBKnowledgePiece kp) {
        for(int i = 0; i < knowledge.size(); i++)
            if(knowledge.get(i).getName().equals(kp.getName()))
                return i;
        return -1;
    }

    public static int getKnowledgeSize() { return knowledge.size(); }

    static {
        new KPBoilingBlood();
        new KPBloodLink();
    }
}
