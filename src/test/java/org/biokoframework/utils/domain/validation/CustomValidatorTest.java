/*
 * Copyright (c) 2014
 *	Mikol Faro			<mikol.faro@gmail.com>
 *	Simone Mangano		<simone.mangano@ieee.org>
 *	Mattia Tortorelli	<mattia.tortorelli@gmail.com>
 *
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package org.biokoframework.utils.domain.validation;

import com.google.inject.Guice;
import com.google.inject.Injector;
import org.biokoframework.utils.domain.DomainEntity;
import org.biokoframework.utils.domain.ErrorEntity;
import org.biokoframework.utils.domain.annotation.field.Field;
import org.biokoframework.utils.validation.ValidationErrorBuilder;
import org.biokoframework.utils.validation.ValidationModule;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date 2014-03-25
 */

public class CustomValidatorTest {

    private IEntityValidator fValidator;

    @Before
    public void createInjector() {
        Injector injector = Guice.createInjector(new ValidationModule());
        IEntityValidatorBuilder validatorBuilder = injector.getInstance(IEntityValidatorBuilder.class);

        fValidator = validatorBuilder.createEntityValidator(CustomValidatedEntity.class);
    }

    @Test
    public void simpleTestWithCustomValidator() {
        CustomValidatedEntity entity = new CustomValidatedEntity();

        entity.set(CustomValidatedEntity.NAME, "pino");

        assertThat(fValidator.isValid(entity), is(false));

        ErrorEntity actualError = fValidator.getErrors().get(0);
        assertThat((Long) actualError.get(ErrorEntity.ERROR_CODE), is(12345L));
        assertThat((String) actualError.get(ErrorEntity.ERROR_MESSAGE), is(equalTo("Name should be 'gino'")));

        entity.set(CustomValidatedEntity.NAME, "gino");

        assertThat(fValidator.isValid(entity), is(true));


    }

    @Validate(alsoWith = { CustomValidator.class })
    public static class CustomValidatedEntity extends DomainEntity {

        private static final long serialVersionUID = 6587026300710308613L;

        @Field
        public static final String NAME = "name";

    }

    public static class CustomValidator implements IAdditionalValidator {

        private ErrorEntity fError;

        @Override
        public boolean isValid(DomainEntity entity) {
            String name = entity.get(CustomValidatedEntity.NAME);
            if ("gino".equals(name)) {
                fError = null;
                return true;
            } else {
                fError = ValidationErrorBuilder.createErrorEntity(12345L, "Name should be 'gino'");
                return false;
            }
        }

        @Override
        public ErrorEntity getError() {
            return fError;
        }

    }
}