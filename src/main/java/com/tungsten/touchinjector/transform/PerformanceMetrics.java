package com.tungsten.touchinjector.transform;

public class PerformanceMetrics {

	volatile long totalTime;
	volatile long matchTime;
	volatile long scanTime;
	volatile long analysisTime;
	volatile long classesScanned;
	volatile long classesSkipped;

	public synchronized long getTotalTime() { return totalTime; }
	public synchronized long getMatchTime() { return matchTime; }
	public synchronized long getScanTime() { return scanTime; }
	public synchronized long getAnalysisTime() { return analysisTime; }
	public synchronized long getClassesScanned() { return classesScanned; }
	public synchronized long getClassesSkipped() { return classesSkipped; }
}