package com.tisser.puneet.tisserartisan.CustomRules;

import com.mobsandgeeks.saripaar.AnnotationRule;

import java.lang.annotation.Annotation;

/**
 * Created by Arun on 18-Sep-15.
 */
public class IsCategorySelectedRule extends AnnotationRule<IsCategorySelected, String>
{
    /**
     * Constructor. It is mandatory that all subclasses MUST have a constructor with the same
     * signature.
     *
     * @param annotation The rule {@link Annotation} instance to which
     *                   this rule is paired.
     */
    protected IsCategorySelectedRule(IsCategorySelected annotation)
    {
        super(annotation);
    }

    @Override
    public boolean isValid(String o)
    {
        boolean isValid = false;

        if(o != null)
            isValid = true;

        // Do some clever validation....

        return isValid;
    }
}
