package by.iba.bussiness.calendar.rrule.frequence;

import by.iba.bussiness.calendar.rrule.constants.DateConstants;
import by.iba.bussiness.calendar.rrule.constants.EnumConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Component
public class FrequencyDefiner {

    private final FrequencyHelper frequencyHelper;

    @Autowired
    public FrequencyDefiner(FrequencyHelper frequencyHelper) {
        this.frequencyHelper = frequencyHelper;
    }

    public Frequency defineFrequency(List<Instant> startDatesOfSessions) {
        final int amountOfDurationsBetweenDates = startDatesOfSessions.size() - 1;
        int amountOfDurationsWhichMultipleToMinute = 0;
        int amountOfDurationsWhichMultipleToHour = 0;
        int amountOfDurationsWhichMultipleToDay = 0;
        int amountOfDurationsWhichMultipleToWeek = 0;
        int freqsToExclude = 0;
        for (int numberOfDates = 0; numberOfDates < amountOfDurationsBetweenDates; numberOfDates++) {
            long timeBetweenSessions = startDatesOfSessions.get(numberOfDates + 1).toEpochMilli() - startDatesOfSessions.get(numberOfDates).toEpochMilli();
            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_WEEK_FREQ) {
                boolean isTimeBetweenSessionsMultipleToWeek = frequencyHelper.isDurationMultipleToFreq(Frequency.WEEKLY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToWeek) {
                    amountOfDurationsWhichMultipleToWeek++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_WEEKLY;
                }
            }

            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_DAILY_FREQ) {
                boolean isTimeBetweenSessionsMultipleToDay = frequencyHelper.isDurationMultipleToFreq(Frequency.DAILY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToDay) {
                    amountOfDurationsWhichMultipleToDay++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_DAILY;
                }
            }

            if (freqsToExclude < DateConstants.VALUE_FOR_EXCLUDE_HOURLY_FREQ) {
                boolean isTimeBetweenSessionsMultipleToHour = frequencyHelper.isDurationMultipleToFreq(Frequency.HOURLY, timeBetweenSessions);
                if (isTimeBetweenSessionsMultipleToHour) {
                    amountOfDurationsWhichMultipleToHour++;
                } else {
                    freqsToExclude += EnumConstants.ORDINAL_OF_HOURLY;
                }
            }

            boolean isTimeBetweenSessionsMultipleToMinute = frequencyHelper.isDurationMultipleToFreq(Frequency.MINUTELY, timeBetweenSessions);
            if (isTimeBetweenSessionsMultipleToMinute) {
                amountOfDurationsWhichMultipleToMinute++;
            }
        }
        List<FrequencyWrapper> candidates = new ArrayList<>();
        Collections.addAll(candidates,
                new FrequencyWrapper(Frequency.WEEKLY, amountOfDurationsWhichMultipleToWeek),
                new FrequencyWrapper(Frequency.DAILY, amountOfDurationsWhichMultipleToDay),
                new FrequencyWrapper(Frequency.HOURLY, amountOfDurationsWhichMultipleToHour),
                new FrequencyWrapper(Frequency.MINUTELY, amountOfDurationsWhichMultipleToMinute));
        return chooseNeededFrequency(candidates, amountOfDurationsBetweenDates);
    }

    private Frequency chooseNeededFrequency(List<FrequencyWrapper> frequencyWrappers, int realAmountOfDurations) {
        Optional<FrequencyWrapper> neededFrequencyWrapper = frequencyWrappers.stream()
                .filter(x -> x.getAmountOfDurationMultipleToFreq() == realAmountOfDurations).findFirst();
        FrequencyWrapper frequencyWrapper = neededFrequencyWrapper.get();
        return frequencyWrapper.getFrequency();
    }
}
