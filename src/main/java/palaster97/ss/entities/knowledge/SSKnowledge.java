package palaster97.ss.entities.knowledge;

import palaster97.ss.entities.knowledge.pieces.*;

import java.util.ArrayList;
import java.util.List;

public class SSKnowledge {

    private static List<SSKnowledgePiece> knowledge = new ArrayList<SSKnowledgePiece>();

    public static void addKnowledgePiece(SSKnowledgePiece kp) {
        if(kp != null) {
            if(knowledge.isEmpty())
                knowledge.add(kp);
            else
                for(SSKnowledgePiece tkp : knowledge)
                    if(tkp.getName().equals(kp.getName())) {
                        System.out.println(tkp.getName() + " has already been registered.");
                        return;
                    }
            knowledge.add(kp);
        }
    }

    public static SSKnowledgePiece getKnowledgePiece(int id) { return knowledge.get(id); }

    public static int getKnowledgePieceID(SSKnowledgePiece kp) {
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
