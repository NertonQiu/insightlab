describe('Recent requests test', function() {
    $('#widget-content').empty().html('');
    var requestsConfig = {
        'title': 'Recent portraits',
          'name': 'recentportraits',
          'json-url': '/reportingService/getPersonicxPortraits',
          'update-url': '/reportingService/updatePersonicxPortrait',
          'delete-url': '/reportingService/deletePersonicxPortrait',
          'save-url': '/reportingService/savePersonicxPortrait',
          'delete-button': {
            'control': 'button', 'title': 'delete',
              'disabled': true, 'name': 'delete-recents'
        }
    };
    var recentPortraits;

    beforeEach(function() {
        $('#widget-content').empty().html('');
        var container = $('<div></div>', {'id': 'recent-portrait'}).appendTo('#widget-content');
        recentPortraits = new RecentRequestsWidget(container);
        recentPortraits.initWidget(requestsConfig);
        recentPortraits.handleChange({'targettype': 'SurveyResponse'});
    });

    it('recent portraits functionality test', function() {
        waits(600);
        runs(function() {
            var data = recentPortraits.getData();
            expect($('#recent-portrait ul li').length).toEqual(data['recents'].length);
            expect(data['recents'].length).toEqual(8);
            expect($('#recent-portrait button').is(':disabled')).toBeTruthy();
            expect($('#recent-portrait button').length).toEqual(1);
            var filter = recentPortraits.getFiltersByPortrait();
            expect(filter.hasOwnProperty('responseID')).toBeTruthy();
            expect(filter.hasOwnProperty('id')).toBeTruthy();
            expect(filter['type']).toEqual('SurveyResponse');
            expect(filter['responseID']).toEqual('1');
            expect(filter['questionID']).toEqual('1');
                $('#recent-portrait ul li:eq(1) input').trigger('click');
                    expect($('#recent-portrait button').is(':disabled')).toBeFalsy();
            recentPortraits.savePortrait({
                widget: {
                    getData: function() {
                        return {
                            'portraitType': {
                                'val': {
                                    'targettype': 'SurveyResponse',
                                    'referencetype': 'SurveyUniverse',
                                    'type': 'Dataset'
                                }
                            },
                            'reportFilters': {
                                'responseID': {'val': 99998},
                                'categoryID': {'val': 1, 'abbreviation': 'New'}
                            },
                            'insights': {'val': 99998}
                        };
                    }
                }
            });
            waits(600);
            runs(function() {
                expect($('#recent-portrait ul li').length).toEqual(data['recents'].length);
                expect(data['recents'].length).toEqual(9);
                $('#recent-portrait button').click();
                waits(600);
                runs(function() {
                expect($('#recent-portrait ul li').length).toEqual(data['recents'].length);
                expect(data['recents'].length).toEqual(8);
                });
            });
        });
    });
});
