package exp

class StatisticController {

    def statisticService

    def index = {
    }

    def genderStatistic = {
        def stat = statisticService.countOpinionBasedOnGender()
        def statColNames = [
                ['string', 'Gender'],
                ['number', 'Count']
        ]
        def statData = []
        stat.each {k, v ->
            if (k =~ /Female.*/ || k =~ /female.*/) {
                statData << ['Female', v]
            } else if (k =~ /Male.*/ || k=~ /male.*/) {
                statData << ['Male', v]
            }
        }
        return [statisticColNames: statColNames, statisticData: statData]
    }
}
