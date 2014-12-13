
describe("Models Page test", function () {
    var model = null;

    beforeEach(function () {
        model = new ModelPage("widget-content");
    });

        afterEach(function () {
            model = null;
            $("#widget-content").html('');
        });

    it("Are table loaded", function () {
        expect($('#widget-content').children('div').length).toEqual(2);
    });

    it("rows length for myModels table (must be 13)", function () {
        waits(2000);
        runs(function () {
            expect($('#widget-content #myModels-widget tbody  tr').length).toEqual(13);
           // var tr = $('#widget-content #myModels-widget tbody tr').length;
           // expect($('#widget-content #myModels-widget tbody input').length).toEqual(tr);

        });
    });

    xit("rows length for myModelLibrary table (must be 3)", function () {
        waits(2000);
        runs(function () {
            expect($('#widget-content #myModelLibrary-widget tbody  tr').length).toEqual(3);
            expect($('#widget-content #myModelLibrary-widget tbody input').length).toEqual(2);

        });
    });

    it("breadcrumb", function () {
        waits(2000);
        runs(function () {
            expect($('#widget-content .acxiom-breadcrumb-container').length).toEqual(1);
        });
    });


    xit("Delete", function () {
        waits(2000);
        runs(function () {
            $("#myModels-widget tbody input:eq(1)").trigger('click');
            $("#myModelLibrary-widget tbody input").trigger('click');
            expect($('#delete-my-model').is(':disabled')).toBeFalsy();
            expect($('#delete_insignt-file').is(':disabled')).toBeFalsy();
        });
    });

    xit("Look-alike Model test", function () {
        waits(2000);
        runs(function () {
            $("a:contains('Look-alike Model'):eq(0)").click();
            waits(2000);
            runs(function() {
                $('zero-records-createModel').click();
                expect($("#lookAlike div")).toEqual('4');
                $('#lookAlike input:eq(0)').val('test');
                $('#lookAlike input:eq(3)').click();
                // $("#lookAlike button:contains('Build Model')").click();
            });
        });
    });


});
