package br.com.buzungobbm.matchbox;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ObjectMatcher {

	public List<BaseFilter> matchObject(Object object, List<BaseFilter> filters) throws ClassNotFoundException {

		final int threadsNumber = (filters != null && !filters.isEmpty()) ? filters.size() : 1;
		final ExecutorService executor = Executors.newFixedThreadPool(threadsNumber);
		final List<Future<BaseFilter>> tasks = new ArrayList<Future<BaseFilter>>();

		for (BaseFilter filter : filters) {
			Callable<BaseFilter> worker = new FilterAnalyzer(object, filter);
			Future<BaseFilter> task = executor.submit(worker);
			tasks.add(task);
		}

		List<BaseFilter> responseFilter = new ArrayList<BaseFilter>();
		for (Future<BaseFilter> task : tasks) {
			try {
				responseFilter.add(task.get());
			} catch (InterruptedException e) {
				System.out.println(e);
			} catch (ExecutionException e) {
				System.out.println(e);
			}
		}
		return responseFilter;
	}


	public boolean allFiltersAreApplyable(List<BaseFilter> filters) {

		for (BaseFilter filter : filters) {
			if (!filter.isApplyable())
				return false;
		}
		
		return true;
	}
	
}
