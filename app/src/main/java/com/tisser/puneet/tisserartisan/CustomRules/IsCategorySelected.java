package com.tisser.puneet.tisserartisan.CustomRules;

import com.mobsandgeeks.saripaar.annotation.ValidateUsing;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@ValidateUsing(IsCategorySelectedRule.class)
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IsCategorySelected {
    public int messageResId()   default -1;                     // Mandatory attribute
    public String message()     default "Please Select Category";   // Mandatory attribute
    public int sequence()       default -1;                     // Mandatory attribute
    public int isVisible()      default  1;
}
