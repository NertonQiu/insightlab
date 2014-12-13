(function() {
    var Modal = {
        toggle : function(id, opt) {
            var self_ = this,
                $modal = $('#' + id),
                $bgCover = $('.acxiom-modal-overlay'),
                showModal = function() {
                    if($bgCover.length === 0) {
                        $bgCover = $('<div/>');
                        $bgCover.addClass('acxiom-modal-overlay')
                                .appendTo('body');
                        if(options.closeonbackgroundclick) {
                            $bgCover.on('click', function() {
                                self_.toggle(id);
                            });
                        }
                    }

                    $('body').css('overflow','hidden');

                    options.modalmt = options.modalheight ? -options.modalheight/2 : -$modal.height()/2;
                    options.modalml = -options.modalwidth/2;

                    $bgCover.fadeIn();
                    $modal.css({
                        'width': options.modalwidth + 'px',
                        'height': options.modalheight + 'px',
                        'margin-top': options.modalmt + 'px',
                        'margin-left': options.modalml + 'px'
                    })
                    .fadeIn();

                    //Hot fix for dataTable component in modal window
                    if($.fn.hasOwnProperty('dataTable')) {
                        $modal.find('.acxiom-table > div').each(function() {
                            var oTableId = $(this).attr('id').replace('_wrapper', '');
                            $('#' + oTableId).dataTable().fnAdjustColumnSizing(false);
                        });
                    }

                    //console.log('opened', $modal);
                },
                hideModal = function() {
                    $bgCover.fadeOut();
                    $modal.fadeOut(function() {
                        $('body').css('overflow','auto');
                    });
                    //console.log('closed', $modal);
                };

            if($modal.length === 0) {
                console.log('cannot find element in DOM', $modal);
                return;
            }

            var options = $.extend({}, {
                'closeonbackgroundclick' : false,
                'modalwidth': 800
            }, opt);

            $modal.is(':hidden') ? showModal() : hideModal();
        }

    };

    //Api
    ACXM.Modal = {
        'toggle': Modal.toggle
    }

    $(function() {
        $('body').on('click', '[data-modal-id], .acxiom-modal-close', function(e) {
            e.preventDefault();

            var modalId = $(this).data('modal-id') ? $(this).data('modal-id') : $(this).parents('.acxiom-modal').attr('id');

            ACXM.Modal.toggle(modalId, {
                'closeonbackgroundclick' : $(this).data('closeonbackgroundclick'),
                'modalwidth': $(this).data('modalwidth'),
                'modalheight': $(this).data('modalheight')
            });
        });
    });

})();




