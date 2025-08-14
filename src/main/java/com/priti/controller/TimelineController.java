package com.priti.controller;

import java.time.LocalDate;

public class TimelineController {
    private LocalDate startDate;
    private LocalDate dueDate;
    
    public TimelineController(LocalDate startDate) {
        this.startDate = startDate;
        this.dueDate = startDate.plusDays(280); // 40 weeks
    }
    
    public LocalDate getStartDate() {
        return startDate;
    }
    
    public void updatePregnancyStartDate(LocalDate newDate) {
        this.startDate = newDate;
        this.dueDate = newDate.plusDays(289);
    }
    
    public LocalDate getToday() {
        return LocalDate.now();
    }
    
    public LocalDate getDueDate() {
        return dueDate;
    }
    
    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
    
    public LocalDate getFirstTrimesterEnd() {
        return startDate.plusWeeks(12);
    }
    
    public LocalDate getSecondTrimesterEnd() {
        return startDate.plusWeeks(26);
    }
    
    public LocalDate getThirdTrimesterEnd() {
        return startDate.plusWeeks(38);
    }
    
    public String getCelebrationMessage() {
        return "";
    }
    
    public boolean isSameDate(LocalDate date1, LocalDate date2) {
        return date1 != null && date2 != null && date1.equals(date2);
    }
}