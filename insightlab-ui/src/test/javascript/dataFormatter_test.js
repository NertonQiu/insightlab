describe('dataFormatter test', function() {

    afterEach(function() {
        $('#widget-content').empty().html('');
    });

    it('test format length', function() {
        var data;
        var dataLoader = new DataLoader();
        dataLoader.loadData('/modelingService/Model', function(result) {
            var dataFormateer = new DataFormatter();
            data = dataFormateer.scorecardFormatter(result);
            expect(data['infoBaseHouseholdCounts'].length).toEqual(10);
            expect(data['lineChartSourceT'].length).toEqual(20);
            expect(data['lineChartSourceV'].length).toEqual(20);
            expect(data['modelGainT'].length).toEqual(20);
            expect(data['modelGainV'].length).toEqual(20);
            expect(data['modelScoreCard'].length).toEqual(34);
        });
    });

    it('test data length', function() {

        var data = {
            'insightDescription': 'insight one',
            'modelDescription': 'In Market',
            'recordCount': 229,
            'index': [110, 103, 167, 113, 88, 103, 133, 69, 79, 83]
        };

        var propensityDetailData = new DataFormatter();
        var result = propensityDetailData.formatPropensityDetailData(data, ['insightDescription', 'modelDescription', 'index']);
        expect(result['rows'].length).toEqual(2);
        expect(result['columns'].length).toEqual(2);
        expect(result['IndexRow'].length).toEqual(10);

    });

    it('test return data length', function() {

        var data = {
            'rows':
            [{
                'questionID': '136',
                'questionText': 'Home Market Value',
                'questionReportText': 'Home Market Value',
                'responseID': '631',
                'responseValue': 'B',
                'responseText': '$25,000 - $49,999',
                'responseReportText': '$25,000 - $49,999',
                'index': 5,
                'zScore': 95.92902940590821,
                'boxedBasePercent': 0.90,
                'boxedTargetCount': 570,
                'boxedTargetTotal': 199939,
                'boxedTargetPercent': 0.002850869,
                'boxedBaseTotal': 236743731,
                'boxedBaseCount': 11672120
            }, {
                'questionID': '136',
                'questionText': 'Home Market Value',
                'questionReportText': 'Home Market Value',
                'responseID': '631',
                'responseValue': 'B',
                'responseText': '$25,000 - $49,999',
                'responseReportText': '$25,000 - $49,999',
                'index': 5,
                'zScore': 95.92902940590821,
                'boxedBasePercent': 0.90,
                'boxedTargetCount': 570,
                'boxedTargetTotal': 199939,
                'boxedTargetPercent': 0.002850869,
                'boxedBaseTotal': 236743731,
                'boxedBaseCount': 11672120
            }
            ]
        };

        var dataFormatter = new DataFormatter();
        var result = dataFormatter.rowDataFormatter(data, ['boxedBaseCount', 'boxedTargetPercent', 'index']);
        expect(result['rows'].length).toEqual(2);
        expect(result['columns'].length).toEqual(3);
        var result2 = dataFormatter.filterPersonicxChartData(data.rows, 'boxedBasePercent');
        expect(result2.length).toEqual(2);

    });
});

