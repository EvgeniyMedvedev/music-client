$(function () {
    $('#createSong').submit(function(event) {
            // Предотвращаем обычную отправку формы
            event.preventDefault();
            createSong();

        });

    function createSong() {
        const album = {albumName:$('#createSong > #albumName').val()};
        const singer = {singerName:$('#createSong > #singerName').val()};
        const user = {
            title:$('#createSong > #title').val(),
            genre:$('#createSong > #genre').val(),
            singer,
            album
        };
        $.ajax({
            type:'POST',
            contentType : "application/json",
            url:$('#createSong').attr('action'),
            data:JSON.stringify(user),
            dataType : 'json',
            success:function () {
                getTable();
            },
            error : function(e) {
                $("#createSong").html("<strong>Error</strong>");
                console.log("ERROR: ", e);
            }
        });
        resetData();
    }

    function resetData(){
        $("#createSong > #title").val("");
        $("#createSong > #genre").val("");
        $("#createSong > #albumName").val("");
        $("#createSong > #singerName").val("");
    }

});