package ua.ivan909020.app;

import org.junit.runner.notification.RunNotifier;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;

public class DataSourceRunner extends BlockJUnit4ClassRunner {

	public final DataSourceHelper dataSourceHelper = DataSourceHelper.getInstance();

	public DataSourceRunner(Class<?> testClass) throws InitializationError {
		super(testClass);
	}

	@Override
	public void run(RunNotifier notifier) {
		beforeRunTests();
		try {
			super.run(notifier);
		} finally {
			afterRunTests();
		}
	}

	private void beforeRunTests() {
		dataSourceHelper.configureDataSource();
	}

	private void afterRunTests() {
		dataSourceHelper.unconfigureDataSource();
	}

}
