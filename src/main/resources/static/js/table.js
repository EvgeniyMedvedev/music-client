$(function () {
    getTable();
});
const html = $('.table');

function getTable() {
    reset();
    $.getJSON('/getAll', function (songs) {
        songs.forEach(function (song) {

            let userElement = song['songId'];

            html.append(`
                <tr>
                   <th scope='row'>` + song['songId'] + `</th>
                   <td>` + song['title'] + `</td>
                   <td>` + song['genre'] + `</td>
                   <td>` + song['singer']['singerName'] + `</td>
                   <td>` + song['album']['albumName'] + `</td>
                   <td>
                           <button onclick='deleteById(` + userElement + `)' class='btn btn-primary'>Удалить</button>
                   </td>
                   <td>
                     <button class='btn btn-primary' data-toggle='modal' data-target=#exampleModal-` + userElement + `>
                       Edit
                    </button>
                                                <!-- Modal Edit -->
                    <div class='modal fade' id=exampleModal-` + userElement + ` tabindex='-1' role='dialog'
                                                     aria-labelledby='exampleModalLabel' aria-hidden='true'>
                                                <div class='modal-dialog' role='document'>
                                                    <div class='modal-content'>
                                                        <div class='modal-header'>
                                                            <h5 class='modal-title' id='exampleModalLabel'>Edit User</h5>
                                                            <button type='button' class='close' data-dismiss='modal'
                                                                    aria-label='Close'>
                                                                <span aria-hidden='true'>&times;</span>
                                                            </button>
                                                        </div>
                <form action=edit&delete.js method='POST' onsubmit='return editById(${song['songId']},this)'>
                                       <div class='modal-body'>
                                                                  <strong>Id</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=${song['songId']} id='song_id' name='songId' required readonly>
                                                                  <strong>Title</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=${song['title']} id='title' name='title' required>
                                                                  <strong>Genre</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=${song['genre']} id='genre' name='genre' required>
                                                                  <strong>Singer Name</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=${song['singer']['singerName']}  id='singerName' name='singerName' required>
                                                                  <strong>Album Name</strong>
                                                                  <input class='d-flex justify-content-center form-control form-control-lg' type='text' value=${song['album']['albumName']} id='albumName' name='albumName' required>
                                                     <div class='modal-footer'>
                                                           <button type='button' class='btn btn-secondary' data-dismiss='modal'>Close</button>
                                                           <button type='submit' class='btn btn-primary'>Edit</button>
                                                     </div>
                                       </div>
                </form>
                                    </div>
                               </div>
                         </div>
                    </td>
                </tr>
                `);
        });

    });
}

function reset() {
    $('.table').val("");
}