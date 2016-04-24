package palaster.bb.api.capabilities.entities;

import java.util.concurrent.Callable;

public class UndeadCapabilityFactory implements Callable<IUndead> {

    @Override
    public IUndead call() throws Exception { return new UndeadCapabilityDefault(); }
}
