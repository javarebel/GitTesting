package org.naveen.maven.research.aspects;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.naveen.maven.research.annotations.NotNull;
import org.naveen.maven.research.annotations.NotNullPolicy;

/**
 * Aspect to process NotNull annotation.
 * 
 * @author <a href='mailto:naveensisupalan@yahoo.com'>Naveen Sisupalan</a>
 *
 */
@Aspect
public class NotNullAspect {
	
	private static final Logger logger = Logger.getLogger(NotNullAspect.class.getName());

	@Around("execution(* org.naveen..*(..,@NotNull (*),..)) && " +
			"(!within(org.naveen.maven.research.annotations.*))")
	public Object doNullParamCheck(ProceedingJoinPoint jp) throws Throwable {
		List<Boolean> lazyNullRtValue = new LinkedList<Boolean>();
		boolean hasToContd = false;
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
						NotNull ntnAnn = (NotNull) anns[i][0];
						logger.log(Level.INFO, String.format("NotNull handling policy is set to %s", ntnAnn.policy()));
						if(args[i] == null) {
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
							case RETURN_NULL_IMMEDIATE:
								return null;
							case RETURN_NULL:
								logger.log(Level.WARNING, "One NotNull annotated argument has null value. Continuing with lazy checking.");
								lazyNullRtValue.add(false);
								break;
							case CONTINUE:
								hasToContd = true;
								lazyNullRtValue.add(true);
								break;
							}
						} else {
							hasToContd = true;
							if(ntnAnn.policy() == NotNullPolicy.RETURN_NULL) 
								lazyNullRtValue.add(true);
						}
					}
				}
			}
		}
		logger.log(Level.INFO, String.format("Argument length is %d and LazyReturn list is %s and hasToContd %s", 
											args.length, lazyNullRtValue, hasToContd));
		if(hasToContd && checkLazyNull(lazyNullRtValue)) 
			return jp.proceed();
		return null;
	}
	
	private boolean checkLazyNull(List<Boolean> list) {
		if(list.size() == 0) return true; 
		for(Boolean b : list)
			if(b) return b;
		return false;
	}
}
