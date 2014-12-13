describe('homePage test', function () {
    var homePage;

    beforeEach(function () {
        homePage = new HomePage('widget-content');
    });

    //    afterEach(function () {
    //        homePage = null;
    //        $("#widget-content").html('');
    //    });

    xit("check recent insights", function () {
        waits(1000);
        runs(function () {
            expect($('#recent-insights div.widget').length).toEqual(4);
        });
    });

    it('check widgets', function () {
        waits(1000);
        runs(function () {
            console.log($('div.widget'));
            expect($('div.widget').length).toEqual(7);
        });
    });
})
















