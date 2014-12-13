;
describe('PanelWidget test', function() {
    var panel = null;
    var panelconfig = {
        'name': 'right-panel',
          'title': 'Portrait filter',
          'sections': [
            { 'name': 'filters',
                'button-class': 'side-icon side-filter',
                'title': 'Portrait Filters',
                'components': [
                    {
                        'name': 'zscore',
                        'firstElement': 'ALL',
                        'max': 3,
                        'control': 'slider',
                        'page-state': 'view-target',
                        'id': 'slider-range-max'

                    },
                    {
                        'name': 'demographic-columns-selector',
                        'visible': true,
                        'control': 'checkbox',
                        'title': 'Demographics',
                        'page-state': 'target'
                    },
                    {
                        'name': 'segment-characteristic',
                        'json-data': [
                            { 'val': 'age', 'text': 'Age' },
                            { 'val': 'income', 'text': 'Income' },
                            { 'val': 'kids', 'text': 'Kids' },
                            { 'val': 'maritalStatus', 'text': 'Marital' },
                            { 'val': 'homeOwnership', 'text': 'Home' },
                            { 'val': 'urbanicity', 'text': 'Urbanicity' },
                            { 'val': 'netWorth', 'text': 'Net Worth' }
                        ],
                        'reset': { 'val': 'reset_dropdown', 'text': 'Reset' },
                        'defaultValue': 'Select a Segment Characteristic',
                        'control': 'dropdown',
                        'page-state': 'view-target',
                        'handled-table': 'portraitChart'
                    },
                    {
                        'name': 'legend',
                        'container-id': 'chart-legend',
                        'control': 'element'
                    }
                ]
            },
            { 'name': 'recents',
                'title': 'Recent Portraits',
                'button-class': 'side-icon side-recent',
                'components': [
                    {
                        'container-id': 'recent-requests',
                        'control': 'element'
                    }
                ]
            },
            {
                'button-class': 'side-icon side-target',
                'name': 'build-target',
                'title': 'Build Target Group',
                'components': [
                    {
                        'name': 'target-name',
                        'control': 'input',
                        'page-state': 'build-target',
                        'placeholder': 'Target Name'
                    },
                    {
                        'name': 'selected-audience',
                        'control': 'progressbar',
                        'title': 'Selected Audience',
                        'page-state': 'build-target',
                        'handled-table': 'portraitChart'
                    },
                    {
                        'title': 'Save Target Group',
                        'page-state': 'build-target',
                        'name': 'save-target',
                        'control': 'button',
                        'disabled': true,
                        'handled-table': 'portraitChart',
                        'cssClass': 'pull-left'
                    },
                    {
                        'title': 'View Saved Targets',
                        'page-state': 'build-target',
                        'name': 'view-saved-targets',
                        'control': 'checkbox-list',
                        'cssClass': 'b-link view-link',
                        'json-url': '/reportingService/getPersonicxTargetGroups',
                        'colored': true

                    }
                ]
            }
        ]
    };

    beforeEach(function() {
        panel = new PanelWidget('widget-content');
        panel.initWidget(panelconfig);
    });

    afterEach(function() {
        panel = null;
        $('#widget-content').empty().html('');
    });

    it('Are panel sections loaded', function() {
        expect(panel.getData()['page-state']).toEqual('view-target');
    });

    xit('check subfilters data elements count. It should contain all subfilters components data + built-target', function() {
        var count = 0;
        for (var k in panel.getData()) {
            if (panel.getData().hasOwnProperty(k)) {
                count++;
            }
        }
        console.log('count', count);
        expect(count).toEqual(panel['children'].length + 1);
    });

    xit('Change selected segment', function() {

        var dropdownContainer = $('#widget-content div.span3');
        var optionContainer = dropdownContainer.find('div.acxiom-select.span12');
        var select = optionContainer.find('select');

        select.val('homeOwnership').trigger('change');
        console.log('data!', panel.getData());
        var optionHome = select.find('option:eq(5)');
        expect(subfilter.getData()['segment-characteristic']).toEqual({ 'disabled': false, 'val': 'homeOwnership', 'text': 'Home' });
        expect(optionHome.text()).toEqual('Home');

        var lastOption = select.find('option:last');
        expect($(lastOption).text()).toEqual('Reset');
        select.val('reset_dropdown').trigger('change');
        expect(subfilter.getData()['segment-characteristic']).toEqual({ 'disabled': false });
    });
});
