package com.konggit.appandroidmvp;

import com.konggit.mvp.mvp.ActivityView;
import com.konggit.mvp.mvp.ContextView;
import com.konggit.mvp.mvp.ModelOps;
import com.konggit.mvp.mvp.PresenterOps;

/**
 * <p>
 * Interface that holds all operations available to MVP layers.
 * Controls the communication process between each layer
 * </p>
 *
 * <strong>Using</strong>
 * <code>interface RequiredViewOps extends ActivityView</code>
 * with VIEW methods to be accessed by PRESENTER
 *
 * <code>interface ProvidedPresenterOps extends PresenterOps<RequiredViewOps></code>
 * Operations offered to VIEW to communicate with PRESENTER
 *
 * <code>interface RequiredPresenterOps</code>
 * with Required PRESENTER methods available to MODEL
 *
 * <code>interface ProvidedModelOps extends ModelOps<RequiredPresenterOps></code>
 * Operations offered to MODEL to communicate with PRESENTER
 *
 * Created by: Tin Megali on 25/02/16. <br/>
 * Project: AndroidMVP </br>
 * --------------------------------------------------- <br />
 * <a href="http://www.tinmegali.com">tinmegali.com</a> <br/>
 * <a href="http://www.github.com/tinmegali>github</a> <br />
 * ---------------------------------------------------
 * <p>
 * Based on
 * <a href="https://github.com/douglascraigschmidt/POSA-15/tree/master/ex/AcronymExpander/src/vandy/mooc">
 * framework MVP</a> developed by
 * <a href="https://github.com/douglascraigschmidt">
 * Dr. Douglas Schmidth</a>
 * </p>
 *
 */

public interface MVP_MainActivity {

    /**
     * Required VIEW methods available to PRESENTER
     *      PRESENTER to VIEW
     */
    interface RequiredViewOps extends ActivityView, ContextView {
    }


    /**
     * Operations offered to VIEW to communicate with PRESENTER
     *      VIEW to PRESENTER
     */
    interface ProvidedPresenterOps extends PresenterOps<RequiredViewOps> {
        boolean clickSaveName(String nameTxt);
        boolean clickClearName();
    }

    /**
     * Required PRESENTER methods available to MODEL
     *      MODEL to PRESENTER
     */
    interface RequiredPresenterOps {
        boolean onNameSaved(String nameTxt);
        boolean onNameCleared();
    }

    /**
     * Operations offered to MODEL to communicate with PRESENTER
     *      PRESENTER to MODEL
     */
    interface ProvidedModelOps extends ModelOps<RequiredPresenterOps> {
        boolean saveName(String nameTxt);
        boolean clearName();
    }

}
