package com.cydai.cncx.common;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

public class BasePresenter<V,M> {
    protected V mView;
    protected M mModule;
    protected CompositeSubscription mCompositeSubscription;

    public BasePresenter(V mView,M mModule){
        this.mView = mView;
        this.mModule = mModule;
    }

    public void addSubscription(Subscription subscriber){

        if(mCompositeSubscription == null)
            mCompositeSubscription = new CompositeSubscription();

        mCompositeSubscription.add(subscriber);
    }

    public void onUnsubscription(){
        if(mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()){
            mCompositeSubscription.unsubscribe();
        }
    }

}
