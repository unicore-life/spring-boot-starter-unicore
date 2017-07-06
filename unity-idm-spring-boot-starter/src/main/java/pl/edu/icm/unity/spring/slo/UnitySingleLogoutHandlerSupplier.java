package pl.edu.icm.unity.spring.slo;

import pl.edu.icm.unity.spring.UnityIdmProperties;
import pl.edu.icm.unity.spring.authn.SingleLogoutHandler;
import pl.edu.icm.unity.spring.saml.SamlSingleLogoutHandler;

public class UnitySingleLogoutHandlerSupplier {
    private final SamlSingleLogoutHandler samlSingleLogoutHandler;
    private final UnityIdmProperties unityIdmProperties;

    private UnitySingleLogoutContext singleLogoutContext = null;

    public UnitySingleLogoutHandlerSupplier(SamlSingleLogoutHandler samlSingleLogoutHandler,
                                            UnityIdmProperties unityIdmProperties) {
        this.samlSingleLogoutHandler = samlSingleLogoutHandler;
        this.unityIdmProperties = unityIdmProperties;
    }

    public UnitySingleLogoutHandlerSupplier withContext(UnitySingleLogoutContext context) {
        singleLogoutContext = context;
        return this;
    }

    public SingleLogoutHandler createSingleLogoutHandler() {
        if (singleLogoutContext == null) {
            throw new NullPointerException("Single logout context should be supplied!");
        }
        return new SingleLogoutHandler(samlSingleLogoutHandler, singleLogoutContext, unityIdmProperties);
    }
}
