describe("Scorecard Page test", function () {

    var scorecard;
    beforeEach(function () {
        scorecard = new Scorecard("widget-content");
    });

        afterEach(function () {
            scorecard = null;
            $("#widget-content").html('');
        });


    xit("Widgets show-Hide functionality ", function () {
        waits(500);
        runs(function () {
            expect($('#Scorecard_for_Model-widget .widget .widget-body').css('display')).toEqual('block');
            expect($('#Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Validation_Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#InfoBase-widget .widget .widget-body').css('display')).toEqual('none');

            expect($('#Variables_Contribution_Chart-widget .widget .widget-body').css('display')).toEqual('block');

            var caret = $('#Gains_Table-widget div.widget div.widget-header a.caret');
            caret.trigger('click');
        });

        waits(500);
        runs(function () {
            expect($('#Scorecard_for_Model-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Gains_Table-widget .widget .widget-body').css('display')).toEqual('block');
            expect($('#Validation_Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#InfoBase-widget .widget .widget-body').css('display')).toEqual('none');

            expect($('#Lift_Chart_for_Gains-widget .widget .widget-body').css('display')).toEqual('block');
            expect($('#Cumulative_Gains_Chart-widget .widget .widget-body').css('display')).toEqual('block');

            var caret = $('#Validation_Gains_Table-widget div.widget div.widget-header a.caret');
            caret.trigger('click');
        });
        waits(500);
        runs(function () {

            expect($('#Scorecard_for_Model-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Validation_Gains_Table-widget .widget .widget-body').css('display')).toEqual('block');
            expect($('#InfoBase-widget .widget .widget-body').css('display')).toEqual('none');

            expect($('#Lift_Chart_Validation_Gain-widget .widget .widget-body').css('display')).toEqual('block');
            expect($('#Cumulative_Gains_Chart_for_Model-widget .widget .widget-body').css('display')).toEqual('block');

            var caret = $('#InfoBase-widget div.widget div.widget-header a.caret');
            caret.trigger('click');
        });
        waits(500);
        runs(function () {
            expect($('#Scorecard_for_Model-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#Validation_Gains_Table-widget .widget .widget-body').css('display')).toEqual('none');
            expect($('#InfoBase-widget .widget .widget-body').css('display')).toEqual('block');

            expect($('#Decile_Chart-widget .widget .widget-body').css('display')).toEqual('block');
        });

    });

    xit("scorechart widgets count (must be 6)", function () {
        waits(500);
        runs(function () {
            expect($('#widget-content .row-fluid .span6:last-child ul.dragable li.scorechart').length).toEqual(6);
        });
    });

    xit("expanded carets count", function () {
        waits(500);
        runs(function () {
            var caret = $('#widget-content .row-fluid .span6:last-child ul.dragable li.scorechart:first-child div.widget div.widget-header a.caret');
            caret.trigger('click');
            expect($('#widget-content .row-fluid .expanded').length).toEqual(1);
        });
    });

    it("widgets containers counts (must be 2)", function () {
        expect($('#widget-content .row-fluid .span6').length).toEqual(2);

    });

    it("Is Breadcrumb", function () {
        expect($('#widget-content .acxiom-breadcrumb-container').length).toEqual(1);

    });

    it("span length (must be 1)", function () {
        expect($('#widget-content .row-fluid .span6:last-child').length).toEqual(1);
        expect($('#widget-content .row-fluid .span6:last-child ul.dragable').length).toEqual(1);

    });


    xit("pop-up", function () {
        //open pop-up
        expect($('#details div').length).toEqual(0);
        $("#detailLink").click();
        waits(1000);
        runs(function () {
            expect($('#details div').length).not.toEqual(0);
            expect($("#details table tbody").length).toEqual(1);
            expect($("#details table tbody input").length).toEqual(13);
            expect($('#details .acxiom-button').is(':disabled')).toBeTruthy();
            $("#details table tbody input:eq(1)").click();
            expect($('#details .acxiom-button').is(':disabled')).toBeFalsy();
            $('#details div.x-btn').click();
            expect($('#details div').length).toEqual(0);
        });
    });


});
