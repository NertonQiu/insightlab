describe('checkbox list widget test', function() {

    var config = {
        'title': 'View Saved Targets',
        'page-state': 'build-target',
        'name': 'view-saved-targets',
        'control': 'checkbox-list',
        'cssClass': 'b-link view-link',
        'json-url': '/reportingService/getPersonicxTargetGroups',
        'colored': true

    };
    var listComponent;
    beforeEach(function() {
        listComponent = new CheckboxListComponent($('#widget-content'));
        listComponent.initWidget(config);
        listComponent.handleEvent();
    });

    afterEach(function() {
        listComponent = null;
        window.location.hash = '';
        $('#widget-content').html('');
    });

    it('check init data', function() {
        waits(600);
        runs(function() {
            expect(listComponent.isOpen()).toBeFalsy();
            $('#widget-content a.view-link').click();
            expect(listComponent.isOpen()).toBeTruthy();
            var data = listComponent.getData();
            expect(data.selectedIds && data.selectedItems).toBeTruthy();
            expect(data.selectedItem).toBeFalsy();
        });
    });

    it('check change data', function() {
        waits(600);
        runs(function() {
            expect($('#widget-content div.checkbox-list').children().length).toEqual(9);
            $('div.checkbox-list input:first').click();
            var data = listComponent.getData();
            expect(data.selectedIds.length === data.selectedItems.length).toBeTruthy();
            expect(data.selectedIds.length).toEqual(1);
            $('div.checkbox-list input:first').click();
            expect(data.selectedIds.length).toEqual(0);
            $('div.checkbox-list input:eq(1)').click();
            $('div.checkbox-list input:eq(3)').click();
            $('div.checkbox-list input:eq(5)').click();
            expect(data.selectedItem.name).toEqual('treeeeqqw');
            expect(data.selectedIds.length).toEqual(3);

        });
    });

    it('add item to list', function() {
        waits(600);
        runs(function() {
            var item = {
                id: '11795386339882905617',
                userID: 'gpundi',
                tenantID: '7fbf1573-6e61-45b6-9073-bab7b6b44443',
                percent: 0.072845474,
                count: 437,
                segments: [
                    '42',
                    '44',
                    '54',
                    '68'
                ],
                name: 'test-save-4',
                segmentsString: null,
                target: {
                    response: null,
                    id: '9371579608718945074',
                    question: 'Insight 15',
                    type: 'Dataset'
                },
                createdDate: '2014-02-03 08:15:40',
                modifiedDate: '2014-02-03 08:15:40',
                reference: {
                    response: null,
                    id: '0',
                    question: 'National',
                    type: 'National'
                }
            };
            listComponent.addListItem(item);
            expect($('#widget-content div.checkbox-list').children().length).toEqual(10);
            $('div.checkbox-list input:eq(0)').click();
            var data = listComponent.getData();
            expect(data.selectedIds.length).toEqual(1);
            expect(data.selectedItem.name).toEqual(item.name);
        });
    });
});
