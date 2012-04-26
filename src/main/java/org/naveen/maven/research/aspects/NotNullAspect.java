package org.naveen.maven.research.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.naveen.maven.research.annotations.NotNull;

@Aspect
public class NotNullAspect {
	
	private static final Logger logger = Logger.getLogger(NotNullAspect.class.getName());

	@Around("execution(public * org.naveen..*(..,@NotNull (*),..)) && " +
			"(!within(org.naveen.maven.research.annotations.*))")
	public Object doNullParamCheck(ProceedingJoinPoint jp) throws Throwable {
		Object[] args = jp.getArgs();
		Signature sig = jp.getSignature();
		if(sig instanceof MethodSignature) {
			MethodSignature mtdSig = MethodSignature.class.cast(sig);
			Method mtd = mtdSig.getMethod();
			logger.log(Level.INFO, String.format("Checking parameters for method --> %s", mtd.getName()));
			Annotation[][] anns = mtd.getParameterAnnotations();
			for(int i=0;i<anns.length;++i) {
				if(anns[i].length > 0) {
					if(anns[i][0] instanceof NotNull) {
						if(args[i] == null) {
							NotNull ntnAnn = (NotNull) anns[i][0];
							logger.log(Level.INFO, String.format("NotNull handling policy is set to %s", ntnAnn.policy()));
							switch(ntnAnn.policy()) {
							case THROW_EXCEPTION:
								Class<? extends Throwable> expCls = ntnAnn.exception();
								logger.log(Level.INFO, String.format("Exception class set is %s", expCls.getName()));
								if(expCls == IllegalArgumentException.class) {
									throw new IllegalArgumentException(
										String.format("In Method %s --> %s", 
												mtd.getName(), ntnAnn.message()));
								} else {
									Constructor<? extends Throwable> ct = expCls.getConstructor(String.class);
									Throwable t = ct.newInstance(String.format("In Method %s --> %s", 
																	mtd.getName(), ntnAnn.message()));
									throw t;
								}
							case RETURN_NULL:
								return null;
							case CONTINUE:
								return jp.proceed();
							}
						} else 
							return jp.proceed();
					}
				}
			}
		}
		return null;
	}
}
