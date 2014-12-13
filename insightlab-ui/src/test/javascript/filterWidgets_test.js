describe('filterWidgets test', function() {

    var filterCofig = {
        'portrait-type': {
            'name': 'portrait-type',
            'mapped-value': true,
            'title': 'Portrait Type',
            'control': 'dropdown',
            'json-data': [
                { 'val': 'analytic-national',
                    'text': 'Analytic Dataset vs National',
                    'disabled': false
                },
                { 'val': 'analytic-survey',
                    'text': 'Analytic Dataset vs Survey',
                    'default': false },
                { 'val': 'survey-national',
                    'text': 'Survey vs National',
                    'default': false }
            ],
            'css-class': 'span3 subfilter',
            'defaultValue': 'Select a Portrait Type'
        },
        'insights': {
            'name': 'insightID',
            'title': 'List of Analytic Datasets',
            'control': 'dropdown',
            'params': { 'isprepping': false },
            'json-url': '/Insights/getInsightsValuesList',
            'css-class': 'span3 subfilter',
            'defaultValue': 'Select an Insight',
            'default': window.location.hash.split('/')[0].replace('#', '')
        },
        'report-filters': [
            {
                'name': 'categoryID',
                'title': 'Category',
                'control': 'dropdown',
                'json-url': '/masterDataService/getAllSynDataCategories',
                'css-class': 'span3 subfilter',
                'defaultValue': 'Select a Category',
                'text-property': 'categoryName',
                'val-property': 'id',
                'abbreviation': 'categoryAbbreviation'
            },
            {
                'name': 'subcategoryID',
                'title': 'Sub-Category',
                'control': 'dropdown',
                'json-url': '/masterDataService/getSynDataSubcategoriesByCategory',
                'prevWidget': 'categoryID',
                'parentVal': 'categoryid',
                'css-class': 'span3 subfilter',
                'defaultValue': 'Select a Sub-Category'
            },
            {
                'name': 'questionID',
                'title': 'Question',
                'control': 'dropdown',
                'json-url': '/masterDataService/getSynDataQuestionsBySubcategory',
                'prevWidget': 'subcategoryID',
                'parentVal': 'subcategoryid',
                'css-class': 'span3 subfilter subfilter-question',
                'defaultValue': 'Select a Question'
            },
            {
                'name': 'responseID',
                'title': 'Response',
                'control': 'dropdown',
                'json-url': '/masterDataService/getSynDataResponsesByQuestion',
                'prevWidget': 'questionID',
                'parentVal': 'questionid',
                'css-class': 'span3 subfilter',
                'defaultValue': 'Select a Response'
            }
        ]

        };
    var reportFilter;
    var portraitType;
    var insights;
    beforeEach(function() {
        var divType = $('<div></div>', {'id': 'portrait-type'}).appendTo('#widget-content');
        var divFilter = $('<div></div>', {'id': 'report-filters'}).appendTo('#widget-content');
        portraitType = new DropDownComponent(divType);
        portraitType.initWidget(filterCofig['portrait-type']);
        window.location.hash = '99998';
        reportFilter = new FilterWidget(divFilter);
        reportFilter.initWidget({ 'name': 'report-filters' });
        reportFilter.handleResult(filterCofig['report-filters']);
    });

    it('change filters...', function() {
        expect($('#portrait-type select option:eq(2)').val()).toEqual('analytic-survey');
        expect(reportFilter.getData().categoryID.disabled).toBeFalsy();
        expect($('#report-filters select').length).toEqual(4);
        waits(600);
        runs(function() {
            if ($.browser.mozilla) {
                $('#report-filters select:first option:eq(2)').attr('selected', true).click();
            }
            else {
                $('#report-filters select:first option:eq(2)').attr('selected', true).change();
            }
            waits(600);
            runs(function() {
                expect(reportFilter.getData().subcategoryID.disabled).toBeFalsy();
                expect($('#report-filters select:eq(1)').is(':disabled')).toBeFalsy();
                if ($.browser.mozilla) {
                    $('#report-filters select:eq(1) option:eq(2)').attr('selected', true).click();
                }
                else {
                    $('#report-filters select:eq(1) option:eq(2)').attr('selected', true).change();
                }
                waits(600);
                runs(function() {
                    expect(reportFilter.getData().questionID.disabled).toBeFalsy();
                    expect($('#report-filters select:eq(2)').is(':disabled')).toBeFalsy();
                    if ($.browser.mozilla) {
                        $('#report-filters select:eq(2) option:eq(2)').attr('selected', true).click();
                    }
                    else {
                        $('#report-filters select:eq(2) option:eq(2)').attr('selected', true).change();
                    }
                    waits(600);
                    runs(function() {
                        expect(reportFilter.getData().responseID.disabled).toBeFalsy();
                        expect($('#report-filters select:eq(3)').is(':disabled')).toBeFalsy();
                        if ($.browser.mozilla) {
                            $('#report-filters select:eq(3) option:eq(2)').attr('selected', true).click();
                        }
                        else {
                            $('#report-filters select:eq(3) option:eq(2)').attr('selected', true).change();
                        }
                    });
                });
            });
        });
    });

    afterEach(function() {
        reportFilter = portraitType = insights = null;
        $('#widget-content').html('');
    });
});
describe('Dropdown component test', function() {
    var staticDropdown = {
        'name': 'segment-characteristic',
        'css-class': 'span3',
        'json-data': [{ 'val': 'age', 'text': 'Age' },
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
    };

    var dropdown;

    beforeEach(function() {
        dropdown = new DropDownComponent('widget-content');
        dropdown.initWidget(staticDropdown);
    });

    afterEach(function() {
        dropdown = null;
        $('#widget-content').empty().html('');
    });

    it('Dropdown load', function() {
        var optionContainer = $('#widget-content div.span12');
        var select = optionContainer.find('select');
        var options = select.find('option');

        expect(optionContainer.length).toEqual(1);
        expect(select.length).toEqual(1);
        expect(options.length).toEqual(staticDropdown['json-data'].length + 1);
        expect(dropdown.getData()).toEqual({ 'disabled': false });
        expect(select.find('option:first').text()).toEqual(staticDropdown['defaultValue']);
    });

    it('Dropdown change', function() {
        var optionContainer = $('#widget-content select');
        console.log($('#widget-content'));
        if ($.browser.mozilla) {
            $('#widget-content select [value="urbanicity"]').attr('selected', true).click();
        }
        else
            $('#widget-content select [value="urbanicity"]').attr('selected', true).change();

        //select.val('urbanicity').trigger('change');
        delete dropdown.getData()['disabled'];
        console.log('current data---', dropdown.getData());
        expect(dropdown.getData()).toEqual(staticDropdown['json-data'][5]);
        if ($.browser.mozilla) {
            $('#widget-content select [value="kids"]').attr('selected', true).click();
        }
        else
            $('#widget-content select [value="kids"]').attr('selected', true).change();
        //  select.val('kids').trigger('change');
        console.log('current data---', dropdown.getData());
        expect(dropdown.getData()).toEqual(staticDropdown['json-data'][2]);
    });
});

describe('Filter widget test', function() {
    $('#widget-content').empty().html('');
    var filterConfig = {
        'name': 'portrait-targetType',
        'title': 'Reference',
        'control': 'dropdown',
        'titleType': 'p',
        'css-class': 'span2',
        'defaultValue': 'Select a Reference',
        'json-data': [
            //{ "val": 'client', "text": "National", 'disabled': false },
            {'val': 'survey', 'text': 'Survey Data', 'default': false }
        ],
        'children': {
            'client':
              [
                  { 'name': 'button', 'id': 'apply', 'cssClass': 'filter-button', 'control': 'save', 'title': 'save', 'parentCss': 'span1 subfilter', 'disabled': false }
              ],
            'survey': [
                {
                    'name': 'categoryID',
                    'title': 'Category',
                    'control': 'dropdown',
                    'json-url': '/masterDataService/getAllSynDataCategories',
                    'css-class': 'span2 subfilter',
                    'defaultValue': 'Select a Category',
                    'text-property': 'categoryName',
                    'val-property': 'id',
                    'abbreviation': 'categoryAbbreviation'
                },
                {
                    'name': 'subcategoryID',
                    'title': 'Sub-Category',
                    'control': 'dropdown',
                    'json-url': '/masterDataService/getSynDataSubcategoriesByCategory',
                    'prevWidget': 'categoryID',
                    'parentVal': 'categoryid',
                    'css-class': 'span2 subfilter',
                    'defaultValue': 'Select a Sub-Category'
                },
                {
                    'name': 'questionID',
                    'title': 'Question',
                    'control': 'dropdown',
                    'json-url': '/masterDataService/getSynDataQuestionsBySubcategory',
                    'prevWidget': 'subcategoryID',
                    'parentVal': 'subcategoryid',
                    'css-class': 'span2 subfilter subfilter-question',
                    'defaultValue': 'Select a Question'
                },
                {
                    'name': 'responseID',
                    'title': 'Response',
                    'control': 'dropdown',
                    'json-url': '/masterDataService/getSynDataResponsesByQuestion',
                    'prevWidget': 'questionID',
                    'parentVal': 'questionid',
                    'css-class': 'span2 subfilter',
                    'defaultValue': 'Select a Response'
                }
            ]
        }

    };

    var filter;

    beforeEach(function() {
        filter = new FilterWidget('widget-content');
        filter.initWidget(filterConfig);
        filter.handleResult(filterConfig['children']['survey']);
    });

    afterEach(function() {
        filter = null;
        $('#widget-content').empty().html('');
    });

    it('Filter load test', function() {
        waits(600);
        runs(function() {
            var dropdownContainers = $('#widget-content');
            expect(dropdownContainers.children().length).toEqual(4);
            expect(dropdownContainers.children('div:eq(0)').find('select').is(':disabled')).toBeFalsy();
            expect(dropdownContainers.children('div:eq(1)').find('select').is(':disabled')).toBeTruthy();
            expect(dropdownContainers.children('div:eq(2)').find('select').is(':disabled')).toBeTruthy();
            expect(dropdownContainers.children('div:eq(3)').find('select').is(':disabled')).toBeTruthy();

            //change category
            console.log('change category...');
            var categoryContainer = dropdownContainers.children('div:eq(0)');
            var categorySelect = categoryContainer.find('select');
            var categoryOption = categorySelect.find('option:last');
            expect(categorySelect.find('option:first').is(':disabled')).toBeTruthy();
            categorySelect.val(categoryOption.text()).trigger('change');
            expect(dropdownContainers.children('div:eq(0)').find('select').is(':disabled')).toBeFalsy();
            expect(dropdownContainers.children('div:eq(1)').find('select').is(':disabled')).toBeFalsy();
            expect(dropdownContainers.children('div:eq(2)').find('select').is(':disabled')).toBeTruthy();
            expect(dropdownContainers.children('div:eq(3)').find('select').is(':disabled')).toBeTruthy();

            //change sub-category
            console.log('change sub-category...');
            waits(600);
            runs(function() {
                var subcategoryContainer = dropdownContainers.children('div:eq(1)');
                var subcategorySelect = subcategoryContainer.find('select');
                var subcategoryOption = subcategorySelect.find('option:last');
                expect(subcategorySelect.find('option:first').is(':disabled')).toBeTruthy();
                subcategorySelect.val(subcategoryOption.text()).trigger('change');
                expect(dropdownContainers.children('div:eq(0)').find('select').is(':disabled')).toBeFalsy();
                expect(dropdownContainers.children('div:eq(1)').find('select').is(':disabled')).toBeFalsy();
                expect(dropdownContainers.children('div:eq(2)').find('select').is(':disabled')).toBeFalsy();
                expect(dropdownContainers.children('div:eq(3)').find('select').is(':disabled')).toBeTruthy();

                //change question
                console.log('change question...');
                waits(600);
                runs(function() {
                    var questionContainer = dropdownContainers.children('div:eq(2)');
                    var questionSelect = questionContainer.find('select');
                    var questionOption = questionSelect.find('option:last');
                    expect(questionSelect.find('option:first').is(':disabled')).toBeTruthy();
                    questionSelect.val(questionOption.text()).trigger('change');
                    expect(dropdownContainers.children('div:eq(0)').find('select').is(':disabled')).toBeFalsy();
                    expect(dropdownContainers.children('div:eq(1)').find('select').is(':disabled')).toBeFalsy();
                    expect(dropdownContainers.children('div:eq(2)').find('select').is(':disabled')).toBeFalsy();
                    expect(dropdownContainers.children('div:eq(3)').find('select').is(':disabled')).toBeFalsy();

                    //change response
                    console.log('change response...');
                    waits(600);
                    runs(function() {
                        var responseContainer = dropdownContainers.children('div:eq(3)');
                        var responseSelect = responseContainer.find('select');
                        var responseOption = responseSelect.find('option:last');
                        expect(responseSelect.find('option:first').is(':disabled')).toBeTruthy();
                        responseSelect.val(responseOption.text()).trigger('change');
                    });
                });
            });
        });
    });
});

