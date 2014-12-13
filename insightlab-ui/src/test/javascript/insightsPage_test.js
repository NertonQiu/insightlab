describe('insightsPage test', function () {

    var insightsPage = null;

    beforeEach(function () {
        insightsPage = new InsightsPage("widget-content");

    });

    afterEach(function () {
        insightsPage = null;
        $("#widget-content").html('');
    });

    it("page load", function () {
        expect($('body')).toBeTruthy();
        var button = $('button.insights-del');
        expect(button).toBeTruthy();
        expect(button.trigger('click')).toBeTruthy();
    });

    it("check delete", function () {
        waits(3500);
        runs(function () {
            expect($('button.insights-del').is(':disabled')).toBeTruthy();
            $('#widget-content tbody input:first').click();
            expect($('button.insights-del').is(':disabled')).toBeFalsy();
        });
    });

    it("reports check", function () {
        waits(2000);
        runs(function () {
            var tr = $("#widget-content tbody tr:eq(2) td:eq(2)");
            //   for (var i = 0; i <= tr.length; i++) {
            tr.click();
            // waits(500);
            expect($('#widget-content tbody tr.info td.details ul li:first a')[0].title).toEqual("Personicx");
            //   }
        });
    });

    it("Recent insights", function () {
        
        waits(1500);
        runs(function () {
            $("button.insights-recent").click();
        });
        
        waits(2000);
            runs(function () {
                expect($('#widget-content tbody tr').length).toEqual(5);
                expect($('#widget-content tbody tr:first td').length).toEqual(8);
            });
        
        //$("button.insights-recent").click();
    });

    it("Check selected on recent", function () {
        expect($('#widget-content tbody input:checked').length).toEqual(0);
        $('#widget-content tbody input:eq(2)').click();

        waits(1000);
        runs(function () {
            $("button.insights-recent").click();
        });

        waits(1000);
        runs(function () {
            $('#widget-content tbody input:eq(3)').click();
            $('#widget-content tbody input:eq(4)').click();
            expect($('#widget-content tbody input:checked').length).toEqual(2);
        });
    });

});