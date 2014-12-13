describe("DPA Page test", function () {

    var profile = null;
    beforeEach(function () {
        var container = document.getElementById("widget-content");
        profile = new ProfileAppPage(container);
    });

    afterEach(function () {
        profile = null;
        $("#widget-content").empty().html('');
    });


    it("dashboard-filters", function () {
        waits(1000);
        runs(function () {
            console.log('window.location.hash', window.location.hash);
                //expect($('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) div.dropdown a').length).toEqual(window.location.hash?10:1);
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(1) .dropdown ul a:eq(2)').click();
        });
        waits(1000);
        runs(function () {
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) .dropdown ul a:eq(2)').click();
        });
        waits(1000);
        runs(function () {
            expect($('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) div.dropdown a:first').text()).toEqual('Social_Media');
            expect($('#chart1').length).toEqual(1);
        });
        waits(1000);
        runs(function () {
            var option = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(3) .dropdown ul a:eq(2)');
            option.click();
            expect($('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(3) div.dropdown a:first').text()).toEqual(option.text());
        });
        waits(1500);
        runs(function () {
            expect($('#dpaReportTable-widget').length).toEqual(1);
            expect($('#chart_div3').length).toEqual(1);
            //expect($('#widget1 .widget .widget-body').css('display')).toEqual('none');
            $('#widget1 .widget .widget-header .caret').click();
            expect($('#widget1 .widget .widget-body').css('display')).toEqual('block');
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(1) div.dropdown ul a:eq(3)').click();
        });

        waits(1000);
        runs(function () {
            expect($('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) div.dropdown a:first').text()).toEqual('Select a Category');
            expect($('.span2.subfilter.subfilter-question').css('display')).toEqual('block');
            $('#widget-content div.row-fluid div.dashboard-filters div.filter-widget-container div.span2:eq(2) div.dropdown ul a:eq(7)').click();
        });
        waits(500);
        runs(function () {
            expect($('.span2.subfilter.subfilter-question').is(':visible')).toEqual(false);
        });
    });


    it("audiencePropensitiesTable", function () {
        waits(1000);
        runs(function () {
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(1) .dropdown ul a:eq(2)').click();
        });
        waits(1000);
        runs(function () {
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) .dropdown ul a:eq(6)').click();
        });
        waits(1000);
        runs(function () {
            
            /** Dropdowns */
            var targetText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(1) div.dropdown a:first').text();
            var catText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(2) div.dropdown a:first').text();
            var referenceText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(0) div.dropdown a:first').text();

            /** Verify title */
            var title = $('#widget1 div.widget-body').children('p');
            expect(title.length).toEqual(3);
            expect(title.eq(0).text()).toEqual("Target: " + targetText);
            expect(title.eq(1).text()).toEqual("Reference: " + referenceText);
            expect(title.eq(2).text()).toEqual("Category: " + catText);
            
            expect($('#chart1').length).toEqual(1);
            $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(3) .dropdown ul a:eq(1)').click();
        });

        waits(1500);
        runs(function () {
            expect($('#audiencePropensitiesTable-widget').length).toEqual(1);
            $('#widget2 .widget .widget-header .caret').click();
            /** Dropdowns */
            waits(500);
            runs(function () {

            var targetText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(1) div.dropdown a:first').text();
            var refText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(0) div.dropdown a:first').text();
            var subCatText = $('#widget-content .row-fluid .dashboard-filters .filter-widget-container div.span2:eq(3) div.dropdown a:first').text();

            /** Verify title */
            var title = $('#widget2 div.widget-body').children('p');
            expect(title.length).toEqual(3);
            expect(title.eq(0).text()).toEqual("Target: " + targetText);
            expect(title.eq(1).text()).toEqual("Reference: " + refText);
            expect(title.eq(2).text()).toEqual("Sub-Category: " + subCatText);
            });
        });
        waits(1500);
        runs(function () {
            expect($('#widget2 .widget .widget-body').css('display')).toEqual('none');
            expect($('#widget1 .widget .widget-body').css('display')).toEqual('block');
        });
    });
});
