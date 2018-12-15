package by.iba.bussines.sender.algorithm.definer;

import by.iba.bussines.sender.algorithm.constants.DateConstants;
import by.iba.bussines.sender.algorithm.constants.EnumConstants;
import by.iba.bussines.sender.algorithm.entity.RruleFreqType;
import by.iba.bussines.sender.algorithm.wrappers.FrequenceWrapper;
import by.iba.bussines.sender.algorithm.wrappers.IntervalWrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class FrequenceIntervalDefiner {

    public FrequenceWrapper defineFreqAndInterval(List<Date> startDatesOfEvents) {
        final int amountOfDurationsBetweenDates = startDatesOfEvents.size() - 1;
        IntervalDefiner timeHandler = new IntervalDefiner();
        int amountOfDurationsWhichMultipleToMinute = 0;
        int amountOfDurationsWhichMultipleToHour = 0;
        int amountOfDurationsWhichMultipleToDay = 0;
        int amountOfDurationsWhichMultipleToWeek = 0;
        long minimumIntervalIfMinute = DateConstants.MINUTES_IN_ONE_HUNDRED_YEARS;
        long minimumIntervalIfHour = DateConstants.HOURS_IN_ONE_HUNDRED_YEARS;
        long minimumIntervalIfDay = DateConstants.DAYS_IN_ONE_HUNDRED_YEARS;
        long minimumIntervalIfWeek = DateConstants.WEEKS_IN_ONE_HUNDRED_YEARS;
        int freqsToExclude = 0;
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenEvents = startDatesOfEvents.get(numberOfDates + 1).getTime() - startDatesOfEvents.get(numberOfDates).getTime();
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_WEEK_FREQ) {
                IntervalWrapper intervalWrapperForWeek = timeHandler.defineInterval(RruleFreqType.WEEKLY, timeBetweenEvents, minimumIntervalIfWeek);
                minimumIntervalIfWeek = intervalWrapperForWeek.getMinimumIntervalOfFreq();
                if (intervalWrapperForWeek.isDurationMultipleToFreq()) {
                    amountOfDurationsWhichMultipleToWeek++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_WEEKLY;
                }
            }
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_DAILY_FREQ) {
                IntervalWrapper intervalWrapperForDay = timeHandler.defineInterval(RruleFreqType.DAILY, timeBetweenEvents, minimumIntervalIfDay);
                minimumIntervalIfDay = intervalWrapperForDay.getMinimumIntervalOfFreq();
                if (intervalWrapperForDay.isDurationMultipleToFreq()) {
                    amountOfDurationsWhichMultipleToDay++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_DAILY;
                }
            }
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_HOURLY_FREQ) {
                IntervalWrapper intervalWrapperForHour = timeHandler.defineInterval(RruleFreqType.HOURLY, timeBetweenEvents, minimumIntervalIfHour);
                minimumIntervalIfHour = intervalWrapperForHour.getMinimumIntervalOfFreq();
                if (intervalWrapperForHour.isDurationMultipleToFreq()) {
                    amountOfDurationsWhichMultipleToHour++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_HOURLY;
                }
            }
            IntervalWrapper intervalWrapperForMinute = timeHandler.defineInterval(RruleFreqType.MINUTELY, timeBetweenEvents, minimumIntervalIfMinute);
            minimumIntervalIfMinute = intervalWrapperForMinute.getMinimumIntervalOfFreq();
            if (intervalWrapperForMinute.isDurationMultipleToFreq()) {
                amountOfDurationsWhichMultipleToMinute++;
            }
        }
        List<FrequenceWrapper> candidates = new ArrayList<>();
        candidates.add(new FrequenceWrapper(RruleFreqType.WEEKLY, amountOfDurationsWhichMultipleToWeek, minimumIntervalIfWeek));
        candidates.add(new FrequenceWrapper(RruleFreqType.DAILY, amountOfDurationsWhichMultipleToDay, minimumIntervalIfDay));
        candidates.add(new FrequenceWrapper(RruleFreqType.HOURLY, amountOfDurationsWhichMultipleToHour, minimumIntervalIfHour));
        candidates.add(new FrequenceWrapper(RruleFreqType.MINUTELY, amountOfDurationsWhichMultipleToMinute, minimumIntervalIfMinute));
        return chooseNeededFrequence(candidates, amountOfDurationsBetweenDates);
    }

    public FrequenceWrapper chooseNeededFrequence(List<FrequenceWrapper> frequenceWrappers, int realAmountOfDurations) {
        Optional<FrequenceWrapper> neededFrequenceWrapper = frequenceWrappers.stream()
                .filter(x -> x.getAmountOfDurationMultipleToFreq() == realAmountOfDurations).findFirst();
        return neededFrequenceWrapper.get();
    }
}
