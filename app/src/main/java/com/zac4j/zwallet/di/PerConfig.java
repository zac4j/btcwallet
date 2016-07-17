package com.zac4j.zwallet.di;

import com.zac4j.zwallet.di.component.PerConfigComponent;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.inject.Scope;

/**
 * A scoping annotation to permit dependencies conform to the life of the
 * {@link PerConfigComponent}
 * Created by zac on 16-7-17.
 */

@Scope @Retention(RetentionPolicy.RUNTIME) public @interface PerConfig {
}
