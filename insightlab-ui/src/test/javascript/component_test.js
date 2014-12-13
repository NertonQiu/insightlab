describe('buttonComponent test', function() {
    var button = null;
    var buttonConfig = [
        {
            'title': 'button test',
            'name': 'button Test',
            'control': 'button',
            'cssClass': 'test1'
        },
        {
            'title': 'disabled test',
            'name': 'disabled button test',
            'control': 'button',
            'disabled': true,
            'cssClass': 'test2'
        },
            {
                'title': 'button test 2',
                'name': 'button test 2',
                'control': 'button',
                'cssClass': 'test3'
            }];

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        for (var i = 0; i < buttonConfig.length; i++) {
            button = new ButtonComponent(container.appendChild(document.createElement('div')));
            button.initWidget(buttonConfig[i]);
        }
    });

    afterEach(function() {
        button = null;
        $('#widget-content').empty().html('');
    });


    it('check buttons', function() {
        waits(500);
        runs(function() {
            expect($('#widget-content button').length).toEqual(buttonConfig.length);
        });
    });

    it('check buttons status', function() {
        expect($('#widget-content button:eq(0):disabled').is(':disabled')).toBeFalsy();
        expect($('#widget-content button:eq(1):disabled').is(':disabled')).toBeTruthy();
        expect($('#widget-content button:eq(2):disabled').is(':disabled')).toBeFalsy();
    });

    it('Check buttons for css Class', function() {
        expect($('#widget-content button:eq(0)').attr('class')).toEqual('btn' + ' ' + buttonConfig[0]['cssClass']);
        expect($('#widget-content button:eq(1)').attr('class')).toEqual('btn' + ' ' + buttonConfig[1]['cssClass']);
        expect($('#widget-content button:eq(2)').attr('class')).toEqual('btn' + ' ' + buttonConfig[2]['cssClass']);
    });

    it('Check buttons names', function() {
        expect($('#widget-content button:eq(0)').text()).toEqual(buttonConfig[0]['title']);
        expect($('#widget-content button:eq(1)').text()).toEqual(buttonConfig[1]['title']);
        expect($('#widget-content button:eq(2)').text()).toEqual(buttonConfig[2]['title']);
    });
});

describe('checkBoxComponent test', function() {
    var checkBox = null;
    var checkBoxes = [];
    var checkBoxConfig = [
        {
            'name': 'checkBox1',
            'default_value': true,
            'control': 'checkbox',
            'title': 'checkBox1'
        }, {
            'name': 'checkBox2',
            'default_value': false,
            'control': 'checkbox',
            'title': 'checkBox2'
        }, {
            'name': 'checkBox3',
            'default_value': false,
            'control': 'checkbox',
            'title': 'checkBox3'
        }
    ];

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        for (var i = 0; i < checkBoxConfig.length; i++) {
            checkBox = new CheckboxComponent(container.appendChild(document.createElement('div')));
            checkBox.initWidget(checkBoxConfig[i]);
            checkBoxes.push(checkBox);
        }
    });

    afterEach(function() {
        checkBox = null;
        checkBoxes = [];
        $('#widget-content').empty().html('');
    });


    it('Check checkbox', function() {
        waits(500);
        runs(function() {
            expect($('#widget-content input').length).toEqual(checkBoxConfig.length);
        });
    });

    it('check setValue function', function() {
        for (var i = 0; i < checkBoxConfig.length; i++) {
            var check = $('#widget-content input[type=checkbox]').eq(i);
            expect(check.is(':checked')).toEqual(checkBoxConfig[i]['default_value']);
            expect(checkBoxes[i].getData()).toEqual(checkBoxConfig[i]['default_value']);
        }
    });

});

describe('sliderComponent test', function() {
    var slider = null;
    var sliderConfig = {
        'name': 'zscore',
        'title': '',
        'firstElement': 'ALL',
        'max': 3,
        'id': 'slider-range-max'
    };

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        slider = new SliderComponent(container);
        slider.initWidget(sliderConfig);
    });

    afterEach(function() {
        slider = null;
        $('#widget-content').empty().html('');
    });

    it('check slider create', function() {
        expect($('#widget-content ul.slider-list').length).toEqual(1);
    });

    it('check slider elements', function() {
        expect($('#widget-content ul.slider-list li').length).toEqual(4);
    });

    it('check slider getData', function() {
        expect($('#widget-content #slider-range-max').length).toEqual(1);

        expect(slider.getData()).toEqual({});
        var $sliderRange = $('#slider-range-max');
        $sliderRange.slider('option', 'change').call($sliderRange, {},{value: 2});
        expect(slider.getData()).toEqual({ selected_val: 2, 'index_step': -1});
    });
});

describe('progressBarComponent check', function() {
    var progressBar = null;
    var progressBarConfig = {
        'name': 'progressBar',
        'title': 'progressBar'
    };
    var data = null;

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        progressBar = new ProgressBarComponent(container);
        progressBar.initWidget(progressBarConfig);
        data = progressBar.getData();
    });

    afterEach(function() {
        progressBar = null;
        $('#widget-content').empty().html('');
    });


    it('check progressBar create', function() {
        expect($('.progressbar')).toBeTruthy();
    });

    it('check progressBar value', function() {
        expect($('.progressbar div.ui-progressbar-value')).toBeTruthy();
    });

    it('check progressBar span name', function() {
        expect($('#widget-content span').text()).toEqual(progressBarConfig['title']);
    });

    it('check progressBar value', function() {
        expect($('.progressbar div.ui-progressbar-value').width()).toEqual(progressBar.getData().percentage && data.percentage);
    });

    xit('check progressBar value', function() {
       /** @const */ var MY_WIDTH = 1842;
        progressBar.setData(1);
        expect($('.progressbar div.ui-progressbar-value').width()).toEqual(MY_WIDTH);
        progressBar.setData(0.5);
        expect($('.progressbar div.ui-progressbar-value').width()).toEqual(MY_WIDTH * progressBar.getData().percentage);
    });

    it('check progressBar value', function() {
        progressBar.setData(0.9);
        expect(progressBar.getData().percentage && data.percentage).toEqual(0.9);
    });
});

describe('inputTagComponent test', function() {
    var inputTag = null;
    var inputTagConfig = {
        'name': 'model-name',
        'placeholder': 'New Model Name',
        'validator': { 'marginTop': '5px' },
        'default-focus': true,
        'default-value': ''
    };

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        inputTag = new InputTagComponent(container);
        inputTag.initWidget(inputTagConfig);
    });

    afterEach(function() {
        inputTag = null;
        $('#widget-content').empty().html('');
    });

    it('check inputTag create', function() {
        expect($('#widget-content input').length).toEqual(1);
    });

    it('check get Element', function() {
        //expect($("#widget-content input")).toEqual(inputTag.getElement());
        //        log(inputTag.getElement());
    });

    it('check setData & getData ', function() {
        var data = inputTag.getData();
        inputTag.setData('Test');
        expect($('#widget-content input').val()).toEqual('Test' && data.text);
    });

    it('check validator true', function() {
        inputTag.setData('Hello test');
        expect(inputTag.getData().text).toEqual('Hello test');
    });

    it('check keyup trigger', function() {
        $('#widget-content input').val('test');
        $('#widget-content input').trigger('keyup');
        expect(inputTag.getData().text === 'test').toBeTruthy();
        expect($('#widget-content input').val()).toEqual('test');
    });

    it('check validator false', function() {
        $('#widget-content input').val('te$t');
        $('#widget-content input').trigger('keyup');
        expect(inputTag.validate()).toBeFalsy();

        inputTag.setData('Test');
        expect(inputTag.validate()).toBeTruthy();

        inputTag.setData('T~st_');
        expect(inputTag.validate()).toBeFalsy();

    });

    it('check default value', function() {
        expect(inputTag.getData() && $('#widget-content input').val()).toEqual(inputTagConfig['default-value']);
    });

    it('check trim-text', function() {
        $('#widget-content input').val('test  test1     ');
        $('#widget-content input').trigger('keyup');
        expect(inputTag.getData().text).toEqual('test test1');
    });

    it('check defaul-value', function() {
        inputTagConfig['default-value'] = 'default';
        inputTag.initWidget(inputTagConfig);
        expect(inputTag.getData().text).toEqual(inputTagConfig['default-value']);
    });
});

describe('dataTableComponent test', function() {
    var dataTable;
    var dataLoader = new DataLoader();
    var options = {
        'name': 'myModelsTable',
        'json-url': '/modelingService/getModelList',
        'top': 20,
        'selectable': true,
        'column-control': false,
        'pagination': false,
        'totals': false,
        'myModelParams': true,
        'choosen': true,
        'isObjectSelect': true,
        'link': {
            'column_number': 1,
            'href': 'scorecards'
        },
        'tableColumns': ['id', 'Description', 'InsightDescription', 'Status', 'CreatedDate'],
        'search': { 'source': 'C' },
        'id_column_name': 'id',
        'uncheck-all': 1,
        'mymodels-status-url': 'srv/notificationService/ModelStatusList'
    };

    // var mapping_ = new ApiMapping;
    //  options['columns'] = mapping_.getMapping(options['tableColumns']);

    beforeEach(function() {
        dataLoader.loadData(options['json-url'], function(result) {
            var container = document.getElementById('widget-content');
            dataTable = new DataTableComponent(container);
            dataTable.setOptions(options);
            dataTable.setData(result['rows']);
            dataTable.draw();
            dataTable.redrawTable();
            dataTable.uncheckAll();
            dataTable.groupTable('insightDescription');
        });
    });

    afterEach(function() {
        dataTable = null;
        $('#widget-content').empty().html('');
    });

    it('check dataTable', function() {
        expect($('#widget-content body')).toBeTruthy();
    });

    xit('check sorting', function() {
        waits(500);
        runs(function() {
            expect($('#widget-content tbody tr:eq(0) td:eq(2)').text()).toEqual('cxcxcxc');
            $('#widget-content thead th:eq(2)').click();
            expect($('#widget-content tbody tr:eq(0) td:eq(2)').text()).toEqual('Testst');
        });
    });

    xit('check getData', function() {
        waits(500);
        runs(function() {
            $('#widget-content tbody input:eq(2)').click();
            var data = dataTable.getData();
            console.log(data);
            expect(data['sObjects'][0]['description']).toEqual($('#widget-content tbody tr:eq(2) td:eq(2)').text());
        });
    });

});

describe('Legend component test', function() {
    var legendComponent;
    var legendConfig = {
        'name': 'legend-widget'
    };
    var data = {
        'Affluent':
            'darkseagreen',
        'Low':
            'orchid',
        'Low Middle':
            'darkorange',
        'Lowest':
	'darkturquoise',
        'Middle':
            'tomato',
        'Upper Middle':
            'palegreen',
        'Wealthy':
	'palevioletred'
    };

    beforeEach(function() {
        var container = document.getElementById('widget-content');
        legendComponent = new LegendComponent(container);
        legendComponent.setData(data);
        legendComponent.initWidget(legendConfig);
        legendComponent.draw();
    });

    afterEach(function() {
        legendComponent = null;
        $('#widget-content').empty().html('');
    });

    it('check legend', function() {
        var number = 0;
        for (var i in data) {
            number += 1;
        }
        expect($('#colored-list li').length).toEqual(number);
    });

    it('check getData', function() {
        var data_ = legendComponent.getData();
        expect(data).toEqual(data_);
    });

});
