package parkandrest.parkingmanagement.core.tariff

import parkandrest.parkingmanagement.api.formula.InvalidFormulaException
import parkandrest.parkingmanagement.core.monetary.Monetary
import java.math.BigDecimal
import javax.script.ScriptEngine
import javax.script.ScriptEngineManager
import javax.script.ScriptException

class Formula(private val rawFormula: String, private val startTariff: Monetary) {

    private val engine: ScriptEngine = ScriptEngineManager().getEngineByName("js")
    private var recursiveOperator: Long = RECURSIVE_OPERATOR_DEFAULT_VALUE
    private var calculationContext: CalculationContext = CalculationContext()

    /**
     * Allows for a calulation that require a context. Especially important for recursive formula usage where different
     * context resets recursiveOperator used by formula to it's default value which is 1
     * @param calculationContext - context for reused formula (especially for recursive formula)
     * @return calculated by formula amount of Monetary
     */
    fun calculate(calculationContext: CalculationContext): Monetary {
        if (this.calculationContext.compareTo(calculationContext) != 0) {
            this.calculationContext = calculationContext
            this.recursiveOperator = RECURSIVE_OPERATOR_DEFAULT_VALUE
        }
        return calculate()
    }

    /**
     * Allows for a simple calculation that does not require context.
     * Increments recursiveOperator with every call.
     * Will throw InvalidFormulaException if formula is invalid
     * @return calculated by formula amount of Monetary
     */
    fun calculate(): Monetary {
        try {
            val result = Monetary(
                    BigDecimal(engine.eval(processRawFormula()).toString()),
                    startTariff.currency
            )
            incrementRecursiveOperator()
            return result
        } catch (exception: ScriptException) {
            throw InvalidFormulaException(
                    arrayOf(rawFormula, startTariff.currency, startTariff.amount),
                    exception.message)
        }

    }

    private fun processRawFormula(): String {
        return rawFormula
                .replace(AMOUNT_PARAMETER_NAME, startTariff.amount.toPlainString())
                .replace(RECURSIVE_PARAMETER_NAME, "" + recursiveOperator)
    }

    private fun incrementRecursiveOperator() {
        recursiveOperator++
    }

    companion object {

        private val AMOUNT_PARAMETER_NAME = "x"
        private val RECURSIVE_PARAMETER_NAME = "i"
        private val RECURSIVE_OPERATOR_DEFAULT_VALUE: Long = 1
    }
}
