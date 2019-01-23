package parkandrest.parkingmanagement.core.tariff


import spock.lang.Specification
import parkandrest.parkingmanagement.api.formula.InvalidFormulaException
import parkandrest.parkingmanagement.core.monetary.Monetary
import parkandrest.parkingmanagement.core.monetary.Currency

import java.math.RoundingMode

class FormulaSpec extends Specification {

    def "should calculate recursive formula"() {
        given:
        def formula = new Formula(
                "(x+2)*Math.pow(2,i)",
                new Monetary(new BigDecimal(3.55).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        def calculatedMonetary = formula.calculate()
        then:
        calculatedMonetary.amount == new BigDecimal(11.1).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should calculate recursive formula with two different results"() {
        given:
        def formula = new Formula(
                "(x+2)*Math.pow(2,i)",
                new Monetary(new BigDecimal(3.55).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        def calculatedMonetary = formula.calculate()
        def anotherMonetary = formula.calculate()
        then:
        calculatedMonetary.amount == new BigDecimal(11.1).setScale(2, RoundingMode.HALF_DOWN)
        anotherMonetary.amount == new BigDecimal(22.2).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should calculate recursive formula with two same results"() {
        given:
        def formula = new Formula(
                "(x+2)*Math.pow(2,i)",
                new Monetary(new BigDecimal(3.55).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        def calculatedMonetary = formula.calculate()
        def anotherMonetary = formula.calculate(new CalculationContext())
        then:
        calculatedMonetary.amount == anotherMonetary.amount
    }

    def "should calculate  formula"() {
        given:
        def formula = new Formula(
                "(x+2)*3",
                new Monetary(new BigDecimal(1.55).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        def calculatedMonetary = formula.calculate().roundToMoneyPrecision()
        then:
        calculatedMonetary.amount == new BigDecimal(10.65).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should calculate simple formula"() {
        given:
        def formula = new Formula(
                "3*3.2",
                new Monetary(new BigDecimal(0).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        def calculatedMonetary = formula.calculate().roundToMoneyPrecision()
        then:
        calculatedMonetary.amount == new BigDecimal(9.6).setScale(2, RoundingMode.HALF_DOWN)
    }

    def "should end with calculation error"(){
        given:
        def formula = new Formula(
                "dsasjd1oiiu13,!!\$^!&*\3!",
                new Monetary(new BigDecimal(0).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        formula.calculate()
        then:
        thrown InvalidFormulaException
    }

    def "should end with another calculation error"(){
        given:
        def formula = new Formula(
                "dsasjd1oiiu13+!!!-sad",
                new Monetary(new BigDecimal(0).setScale(2, RoundingMode.HALF_DOWN), Currency.USD)
        )
        when:
        formula.calculate()
        then:
        thrown InvalidFormulaException
    }
}
