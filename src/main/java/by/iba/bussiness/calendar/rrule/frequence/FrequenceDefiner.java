package by.iba.bussiness.calendar.rrule.frequence;


import by.iba.bussiness.calendar.rrule.constants.DateConstants;
import by.iba.bussiness.calendar.rrule.constants.EnumConstants;
import by.iba.bussiness.calendar.rrule.frequence.model.RruleFreqType;
import by.iba.bussiness.calendar.rrule.frequence.wrapper.FrequenceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
public class FrequenceDefiner {

    private FrequenceHelper frequenceHelper;

    @Autowired
    public FrequenceDefiner(FrequenceHelper frequenceHelper) {
        this.frequenceHelper = frequenceHelper;
    }


    public RruleFreqType defineFrequence(List<Date> startDatesOfSessions) {
        final int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        int amountOfDurationsWhichMultipleToMinute = 0;
        int amountOfDurationsWhichMultipleToHour = 0;
        int amountOfDurationsWhichMultipleToDay = 0;
        int amountOfDurationsWhichMultipleToWeek = 0;
        int freqsToExclude = 0;
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).getTime() - startDatesOfSessions.get(numberOfDates).getTime();
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_WEEK_FREQ) {
                boolean isTimeBetweenSessionsMultipleToWeek = frequenceHelper.isDurationMultipleToFreq(RruleFreqType.WEEKLY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToWeek) {
                    amountOfDurationsWhichMultipleToWeek++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_WEEKLY;
                }
            }
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_DAILY_FREQ) {
                boolean isTimeBetweenSessionsMultipleToDay = frequenceHelper.isDurationMultipleToFreq(RruleFreqType.DAILY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToDay) {
                    amountOfDurationsWhichMultipleToDay++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_DAILY;
                }
            }
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_HOURLY_FREQ) {
                boolean isTimeBetweenSessionsMultipleToHour = frequenceHelper.isDurationMultipleToFreq(RruleFreqType.HOURLY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToHour) {
                    amountOfDurationsWhichMultipleToHour++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_HOURLY;
                }
            }
            boolean isTimeBetweenSessionsMultipleToMinute = frequenceHelper.isDurationMultipleToFreq(RruleFreqType.MINUTELY, timeBetweenSessions);
            if (isTimeBetweenSessionsMultipleToMinute) {
                amountOfDurationsWhichMultipleToMinute++;
            }
        }
        List<FrequenceWrapper> candidates = new ArrayList<>();
        candidates.add(new FrequenceWrapper(RruleFreqType.WEEKLY, amountOfDurationsWhichMultipleToWeek));
        candidates.add(new FrequenceWrapper(RruleFreqType.DAILY, amountOfDurationsWhichMultipleToDay));
        candidates.add(new FrequenceWrapper(RruleFreqType.HOURLY, amountOfDurationsWhichMultipleToHour));
        candidates.add(new FrequenceWrapper(RruleFreqType.MINUTELY, amountOfDurationsWhichMultipleToMinute));
        return chooseNeededFrequence(candidates, amountOfDurationsBetweenDates);
    }

    public RruleFreqType chooseNeededFrequence(List<FrequenceWrapper> frequenceWrappers, int realAmountOfDurations) {
        Optional<FrequenceWrapper> neededFrequenceWrapper = frequenceWrappers.stream()
                .filter(x -> x.getAmountOfDurationMultipleToFreq() == realAmountOfDurations).findFirst();
        FrequenceWrapper frequenceWrapper = neededFrequenceWrapper.get();
        return frequenceWrapper.getRruleFreqType();
    }
}
