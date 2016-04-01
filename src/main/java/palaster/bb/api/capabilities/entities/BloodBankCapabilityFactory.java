package palaster.bb.api.capabilities.entities;

import java.util.concurrent.Callable;

public class BloodBankCapabilityFactory implements Callable<IBloodBank> {

    @Override
    public IBloodBank call() throws Exception { return new BloodBankCapabilityDefault(); }
}
