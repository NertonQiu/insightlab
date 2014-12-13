;
describe('TargetGroups test', function() {
    var targetList = null;
    var targetconfig = {
                'control': 'target-group-list',
                'name': 'view-saved-targets',
                'handled-table': 'portraitChart',
                'input': {
                    'name': 'target-name',
                    'control': 'input',
                    'page-state': 'build-target',
                    'placeholder': 'Target Name'
                },
                'save-button': {
                    'title': 'Save Target Group',
                    'page-state': 'build-target',
                    'name': 'save-target',
                    'control': 'button',
                    'disabled': true,
                    'cssClass': 'pull-left'
                },
                'targets-list': {
                    'title': 'View Saved Targets',
                    'page-state': 'build-target',
                    'name': 'targets-list',
                    'control': 'checkbox-list',
                    'cssClass': 'b-link view-link',
                    'json-url': '/reportingService/getPersonicxTargetGroups',
                    'colored': true

                },
                'selected-audience': {
                    'name': 'selected-audience',
                    'control': 'progressbar',
                    'title': 'Selected Audience',
                    'page-state': 'build-target',
                    'handled-table': 'portraitChart'
                }
      };
    var context = {
        portraitType: {
            text: 'Analytic Dataset vs National',
            val: {
                referencetype: 'National',
                targettype: 'Dataset',
                type: 'analytic-national'
            }
        },
        insights: {
            disabled: false,
            text: 'Insight 15',
            val: '9371579608718945074'
        },
        reportFilters: null,
        report: 'portrait'
    };
    beforeEach(function() {
        targetList = new TargetGroupsList($('#widget-content'));
        targetList.initWidget(targetconfig);
   });

    afterEach(function() {
        targetList = null;
        $('#widget-content').empty().html('');
    });

    it('Are panel sections loaded', function() {
        expect(targetList.getData()).toBeTruthy('view-target');
    });

    it('check data elements count. It should contain all subfilters components data + built-target', function() {
        var count = 0;
        for (var k in targetList.getData()) {
            if (targetList.getData().hasOwnProperty(k)) {
                count++;
            }
        }
        expect(count).toEqual(3);
    });

    it('if Target Group saved', function() {
        targetList.handleEvent(context);
        targetList.handleTableChange({'widget': {
            getData: function() {
                return { sIds: ['4', '8'],
                    sObjects: [{basePercent: 2}, {basePercent: 3}]};
            }
        }});
        var targetData = targetList.getData();
        expect(targetData['selected-rows'].length).toEqual(2);
        $('#widget-content').find('input[type="text"]').val('test-1');
        $('#widget-content').find('input[type="text"]').keyup();
        expect(targetData['target-name'].text).toEqual('test-1');
        $('#widget-content').find('button').click();
         targetData = targetList.getData();

        waits(500);
        runs(function() {
        expect(targetData['selected-rows'].length).toEqual(0);
        expect(targetData['target-name'].text).toEqual('');
        });
    });
    it('if error', function() {
        targetList.handleEvent(context);
        targetList.handleTableChange({'widget': {
            getData: function() {
                return { sIds: ['4', '8'],
                    sObjects: [{basePercent: 2}, {basePercent: 3}]};
            }
        }});

        var targetData = targetList.getData();
        expect(targetData['selected-rows'].length).toEqual(2);
        $('#widget-content').find('input[type="text"]').val('test$');
        $('#widget-content').find('input[type="text"]').keyup();
        expect(targetData['target-name'].text).toEqual('test$');
        $('#widget-content').find('button').click();
        targetData = targetList.getData();

        waits(500);
        runs(function() {
            expect(targetData['selected-rows'].length).toEqual(2);
            expect(targetData['target-name'].text).toEqual('test$');

        });
    });
});
