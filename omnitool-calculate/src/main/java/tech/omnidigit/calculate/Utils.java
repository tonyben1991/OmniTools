package tech.omnidigit.calculate;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.util.List;

public class Utils {

    public static String dateFormat = "yyyy-MM-dd";

    public static BigDecimal getIrr(List<BigDecimal> netCashFlows, BigDecimal... guess) {
        int loopNum = 100;
        BigDecimal minDiff = new BigDecimal("0.0000001");

        BigDecimal flowOut = netCashFlows.get(0);
        BigDecimal minValue = BigDecimal.ONE.negate();
        BigDecimal maxValue = BigDecimal.ONE;
        BigDecimal tempValue = guess.length > 0 ? guess[0] : new BigDecimal("0.1");
        while (loopNum > 0) {
            BigDecimal npv = getNpv(tempValue, netCashFlows);
            if (flowOut.add(npv).abs().compareTo(minDiff) < 0) {
                break;
            } else if (flowOut.add(npv).multiply(flowOut).compareTo(BigDecimal.ZERO) < 0) {
                minValue = tempValue;
            } else {
                maxValue = tempValue;
            }
            tempValue = minValue.add(maxValue).divide(new BigDecimal(2), 20, RoundingMode.HALF_UP);
            loopNum--;
        }
        return tempValue;
    }

    /**
     * NPV
     *
     * @param rate         折现率
     * @param netCashFlows 现金流
     * @return
     */
    public static BigDecimal getNpv(BigDecimal rate, List<BigDecimal> netCashFlows) {
        BigDecimal npv = BigDecimal.ZERO;
        for (int i = 1; i < netCashFlows.size(); i++) {
            npv = npv.add(netCashFlows.get(i).divide(rate.add(BigDecimal.ONE).pow(i), 20, RoundingMode.HALF_UP));
        }
        return npv;
    }

    public static BigDecimal getXirr(List<BigDecimal> netCashFlows, List<String> dates, BigDecimal... guess) throws ParseException {
         int loopNum = 100;
        BigDecimal minDiff = new BigDecimal("0.0000001");

        BigDecimal flowOut = netCashFlows.get(0);
        BigDecimal minValue = BigDecimal.ONE.negate();
        BigDecimal maxValue = BigDecimal.ONE;
        BigDecimal tempValue = guess.length > 0 ? guess[0] : new BigDecimal("0.1");
        while (loopNum > 0) {
            BigDecimal xnpv = getXnpv(tempValue, netCashFlows,dates);
            if (flowOut.add(xnpv).abs().compareTo(minDiff) < 0) {
                break;
            } else if (flowOut.add(xnpv).multiply(flowOut).compareTo(BigDecimal.ZERO) < 0) {
                minValue = tempValue;
            } else {
                maxValue = tempValue;
            }
            tempValue = minValue.add(maxValue).divide(new BigDecimal(2), 20, RoundingMode.HALF_UP);
            loopNum--;
        }
        return tempValue;
    }

    /**
     * XNPV
     *
     * @param rate         折现率
     * @param dates        日期列表
     * @param netCashFlows 现金流
     * @return
     */
    public static BigDecimal getXnpv(BigDecimal rate, List<BigDecimal> netCashFlows, List<String> dates) throws ParseException {
        String startDate = dates.get(0);
        BigDecimal xnpv = BigDecimal.ZERO;
        for (int i = 0; i < netCashFlows.size(); i++) {
            BigDecimal netCashFlow = netCashFlows.get(i);
            String date = dates.get(i);

            if (startDate.equals(date)) {
                xnpv = xnpv.add(netCashFlow);
            } else {
                BigDecimal temp = BigDecimal.valueOf(Math.pow(rate.add(BigDecimal.ONE).doubleValue(), tech.omnidigit.date.Utils.getIntervalDays(startDate, date, dateFormat)));
                xnpv = xnpv.add(netCashFlow.divide(temp, 20, RoundingMode.HALF_UP));
            }
        }
        return xnpv;
    }

}
