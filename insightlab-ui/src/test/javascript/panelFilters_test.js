describe('panelWidget test', function() {
    var filterCofig = {
        control: 'filters-list',
        'name': 'filters-list',
        'zscore': {
            'name': 'zscore',
            'firstElement': 'ALL',
            'max': 3,
            // 'title': 'Filter by Z-Score',
            'control': 'slider',
            'page-state': 'view-target',
            'id': 'slider-range-max'

        },
        'demographic': {
            'name': 'demographic-columns-selector',
            'visible': true,
            'control': 'checkbox',
            'title': 'Demographics',
            'page-state': 'target'
        },
        'segment': {
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
        'legend': {
            'name': 'legend',
            'container-id': 'chart-legend',
            'control': 'element'
        }
    };
    var panelFilter;
    beforeEach(function() {
        panelFilter = new PanelFilters($('#widget-content'));
        panelFilter.initWidget(filterCofig);
    });

    afterEach(function() {
        panelFilter = null;
        //$('#widget-content').html('');
    });

    it('change panel filters section', function() {
        var filterData = panelFilter.getData();
        expect(filterData['demographic-columns-selector']).toBeFalsy();
        expect(filterData['zscore']).toBeTruthy();
        expect(filterData['segment-characteristic']).toBeTruthy();
        var $sliderRange = $('#slider-range-max');
        $sliderRange.slider('option', 'change').call($sliderRange, {},{value: 2});
        expect(filterData['zscore']['selected_val']).toEqual(2);
        $('label input[type="checkbox"]').click();
        $('label input[type="checkbox"]').change();
            filterData = panelFilter.getData();
            expect(filterData['demographic-columns-selector']).toBeTruthy();
    });
});

