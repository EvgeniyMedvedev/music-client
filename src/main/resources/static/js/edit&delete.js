$(function () {
    $.fn.serializeFormJSON = function () {
        const o = {};
        const a = this.serializeArray();

        $.each(a, function () {
            if (o[this.name]) {
                if (!o[this.name].push) {
                    o[this.name] = [o[this.name]];
                }
                o[this.name].push(this.value || '');
            } else {
                o[this.name] = this.value || '';
            }
        });
        return o;
    };
})(jQuery);
function editById(id,form) {

    let $form = $(form);
    const data = new FormData($form.serializeFormJSON());
    const album = {albumName:$('#albumName').val()};
    const singer = {singerName:$('#singerName').val()};
    data.append("album", JSON.stringify(album));
    data.append("singer", JSON.stringify(singer));

    $.ajax({
        type: $form.attr('method'),  // метод который ты будешь использовать для отправки,
        //можно написать текстом, а можно взять с формы...
        //так как у нашей формы есть аттрибут method то я беру это с формы
        url: 'http://localhost:8080/update',//$form.attr('action'), // url на который ты будешь отправлять данные,
         //можно написать текстом, а можно взять с формы...так как у нашей формы есть аттрибут action то
         //я беру это с формы
        data: JSON.stringify(data),  // сами данные это либо string либо array,
        //можно написать ручками но беру данные с форм
        dataType : 'json',
        success: function(){  // try success
            alert('Данные отправлены');
            location.reload();
        },
        error: function() { // обработка исключения
            alert('Данные не отправлены')
        }
    });

    return false;
}
function deleteById(id) {
    $.getJSON('http://localhost:8080/get/' + id,function (song) {
        deleteUser();

        function deleteUser(){
            let data = {
                title:song['title'],
                uuid:id
            };

            $.ajax({
                type:'DELETE',
                contentType : "application/json",
                url:'http://localhost:8080/delete',
                data:JSON.stringify(data.uuid),
                dataType : 'json',
                success:function () {
                    getTable();
                },
                error : function(e) {
                    $(".table").append("<strong>Error"+ e +"</strong>");
                    getTable();
                }
            })
        }
    })
}