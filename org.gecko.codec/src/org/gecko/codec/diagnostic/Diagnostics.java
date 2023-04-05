/**
 * Copyright (c) 2012 - 2023 Data In Motion and others.
 * All rights reserved. 
 * 
 * This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0
 * which is available at https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 * 
 * Contributors:
 *     Data In Motion - initial API and implementation
 */
package org.gecko.codec.diagnostic;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.List;
import java.util.OptionalInt;

/**
 * Diagnostics helper class
 * @author Mark Hoffmann
 * @since 21.03.2023
 */
public class Diagnostics {

	static class DiagnosticImpl implements Diagnostic {

		private final Severity severity;
		private final String message;
		private final Throwable throwable;
		private boolean stopper = false;

		/**
		 * Creates a new instance.
		 */
		DiagnosticImpl(Severity severity, String message, Throwable throwable, boolean stopper) {
			requireNonNull(severity);
			requireNonNull(message);
			this.severity = severity;
			this.message = message;
			this.throwable = throwable;
			this.stopper = stopper;
		}

		/* 
		 * (non-Javadoc)
		 * @see org.gecko.codec.diagnostic.Diagnostic#getSeverity()
		 */
		@Override
		public Severity getSeverity() {
			return severity;
		}

		/* 
		 * (non-Javadoc)
		 * @see org.gecko.codec.diagnostic.Diagnostic#getMessage()
		 */
		@Override
		public String getMessage() {
			return message;
		}

		/* 
		 * (non-Javadoc)
		 * @see org.gecko.codec.diagnostic.Diagnostic#getError()
		 */
		@Override
		public Throwable getError() {
			return throwable;
		}

		/* 
		 * (non-Javadoc)
		 * @see org.gecko.codec.diagnostic.Diagnostic#isStopper()
		 */
		@Override
		public boolean isStopper() {
			return stopper;
		}

	}

	public static Diagnostic createInfoDiagnostic(String message) {
		return createDiagnostic(Severity.INFO, message, null, false);
	}

	public static Diagnostic createInfoDiagnostic(String message, Throwable t) {
		return createDiagnostic(Severity.INFO, message, t, false);
	}

	public static Diagnostic createWarningDiagnostic(String message) {
		return createDiagnostic(Severity.WARNING, message, null, false);
	}

	public static Diagnostic createWarningDiagnostic(String message, Throwable t) {
		return createDiagnostic(Severity.WARNING, message, t, false);
	}


	public static Diagnostic createErrorDiagnostic(String message) {
		return createDiagnostic(Severity.ERROR, message, null, false);
	}

	public static Diagnostic createErrorDiagnostic(String message, Throwable t) {
		return createDiagnostic(Severity.ERROR, message, t, false);
	}

	public static Diagnostic createErrorDiagnostic(String message, Throwable t, boolean stopper) {
		return createDiagnostic(Severity.ERROR, message, t, stopper);
	}

	public static Diagnostic createDiagnostic(Severity severity, String message, Throwable t, boolean stopper) {
		return new DiagnosticImpl(severity, message, t, stopper);
	}

	public static Diagnostic createDiagnostic(Throwable t) {
		requireNonNull(t);
		return createDiagnostic(Severity.ERROR, t.getMessage(), t, false);
	}

	public static AggregatedDiagnostic createAggregated(List<Diagnostic> diagnostics, String message, Throwable throwable) {
		return createAggregated(diagnostics, null, message, throwable, null);
	}
	
	public static AggregatedDiagnostic createAggregated(List<Diagnostic> diagnostics, String message) {
		return createAggregated(diagnostics, null, message, null, null);
	}
	
	public static AggregatedDiagnostic createAggregated(List<Diagnostic> diagnostics, Severity severity, String message, Throwable throwable, Boolean stopper) {
		if (diagnostics == null) {
			diagnostics = Collections.emptyList();
		}
		List<Diagnostic> diagnosticCopy = Collections.unmodifiableList(diagnostics);
		return new AggregatedDiagnostic() {

			@Override
			public boolean isStopper() {
				if (isNull(stopper)) {
					return diagnosticCopy.
							stream().
							filter(Diagnostic::isStopper).
							findFirst().
							isPresent();
				} else {
					return stopper.booleanValue();
				}
			}

			@Override
			public Severity getSeverity() {
				if (isNull(severity)) {
					OptionalInt severityInt = diagnosticCopy.
							stream().
							mapToInt(d->d.getSeverity().ordinal()).
							max();
					if (severityInt.isPresent()) {
						return Severity.values()[severityInt.getAsInt()];
					} else {
						return Severity.ERROR;
					}
				} else {
					return severity;
				}
			}

			@Override
			public String getMessage() {
				if (isNull(message)) {
					if (nonNull(throwable)) {
						return throwable.getMessage();
					} else {
						return AggregatedDiagnostic.NO_MESSAGE;
					}
				} else {
					return message;
				}
			}

			@Override
			public Throwable getError() {
				return throwable;
			}

			@Override
			public List<Diagnostic> getDiagnostics() {
				return diagnosticCopy;
			}
		};
	}
}
