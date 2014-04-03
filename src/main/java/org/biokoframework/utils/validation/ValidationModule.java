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

package org.biokoframework.utils.validation;

import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;
import com.google.inject.name.Names;
import org.biokoframework.utils.domain.validation.IEntityValidatorBuilder;
import org.biokoframework.utils.validation.impl.*;
import org.joda.time.DateTime;
import org.joda.time.LocalDate;

/**
 * 
 * @author Mikol Faro <mikol.faro@gmail.com>
 * @date Mar 4, 2014
 *
 */
public class ValidationModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(new TypeLiteral<ITypeValidator<String>>() {})
			.to(StringValidator.class);
		
		bind(new TypeLiteral<ITypeValidator<Long>>() {})
			.to(LongValidator.class);

        bind(new TypeLiteral<ITypeValidator<Boolean>>() {})
            .to(BooleanValidator.class);

		bind(new TypeLiteral<ITypeValidator<String>>() {})
			.annotatedWith(Names.named("email"))
			.to(EmailValidator.class);
		
		bind(new TypeLiteral<ITypeValidator<LocalDate>>() {})
			.to(LocalDateValidator.class);
		
		bind(new TypeLiteral<ITypeValidator<DateTime>>() {})
			.to(DateTimeValidator.class);
		
		bind(IEntityValidatorBuilder.class)
			.to(InjectedValidatorBuilder.class);

	}

}
