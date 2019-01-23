package parkandrest.parkingmanagement.core.tariff


import spock.lang.Specification
import parkandrest.parkingmanagement.api.tariff.IllegalOrderException
import parkandrest.parkingmanagement.core.stubfactory.EntitiesStubFactory

class TarifficationRulesCollectionSpec extends Specification {

    def "should get rules for all specified orders"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.recursiveRegularTarifficationRule(tariff),
                EntitiesStubFactory.secondRegularTarifficationRule(tariff),
        )
        when:
        def firstRule = tariff.tarifficationRulesCollection.getRuleByOrder(1)
        def secondRule = tariff.tarifficationRulesCollection.getRuleByOrder(2)
        def thirdRule = tariff.tarifficationRulesCollection.getRuleByOrder(3)
        def thirdRuleWithDiffFormula = tariff.tarifficationRulesCollection.getRuleByOrder(4)
        then:
        firstRule.ruleOrder == RuleOrder.FIRST
        secondRule.ruleOrder == RuleOrder.SECOND
        thirdRule.ruleOrder == RuleOrder.RECURSIVE_NEXT
        thirdRuleWithDiffFormula.ruleOrder == RuleOrder.RECURSIVE_NEXT
    }

    def "should get first rule for every positive order"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff)
        )
        when:
        def firstRule = tariff.tarifficationRulesCollection.getRuleByOrder(3)
        def anotherFirstRule = tariff.tarifficationRulesCollection.getRuleByOrder(17)
        then:
        firstRule.ruleOrder == RuleOrder.FIRST
        anotherFirstRule.ruleOrder == RuleOrder.FIRST
    }

    def "should get second rule for every positive order"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.secondRegularTarifficationRule(tariff)
        )
        when:
        def secondRule = tariff.tarifficationRulesCollection.getRuleByOrder(3)
        def anotherSecondRule = tariff.tarifficationRulesCollection.getRuleByOrder(17)
        then:
        secondRule.ruleOrder == RuleOrder.SECOND
        anotherSecondRule.ruleOrder == RuleOrder.SECOND
    }

    def "should throw an error because order value is incorrect"() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff)
        )
        when:
        tariff.tarifficationRulesCollection.getRuleByOrder(0)
        then:
        thrown IllegalOrderException
    }

    def "should add and then override every rule in collection "() {
        given:
        def parking = EntitiesStubFactory.parking()
        def tariff = EntitiesStubFactory.regularTariff(parking)
        def overridingFirstRule = new TarifficationRule(tariff, RuleOrder.FIRST, "x+5")
        def overridingSecondRule = EntitiesStubFactory.secondVipTarifficationRule(tariff)
        def overridingThirdRule = EntitiesStubFactory.recursiveVipTarifficationRule(tariff)
        and:
        tariff.tarifficationRulesCollection.addTarifficationRule(
                EntitiesStubFactory.firstTarifficationRule(tariff),
                EntitiesStubFactory.secondRegularTarifficationRule(tariff),
                EntitiesStubFactory.recursiveRegularTarifficationRule(tariff),

        )
        when:
        tariff.tarifficationRulesCollection.addTarifficationRule(overridingFirstRule, overridingSecondRule, overridingThirdRule)
        then:
        tariff.tarifficationRulesCollection.getTarifficationRules().size() == 3
        tariff.tarifficationRulesCollection.getTarifficationRules().rawFormula.sort() ==
                [overridingFirstRule.rawFormula, overridingSecondRule.rawFormula, overridingThirdRule.rawFormula].sort()
    }
}
