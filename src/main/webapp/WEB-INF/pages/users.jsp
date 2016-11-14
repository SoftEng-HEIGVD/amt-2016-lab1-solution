<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<t:container>
  <jsp:attribute name="head">
    <link rel="stylesheet" href="https://cdn.datatables.net/1.10.12/css/dataTables.bootstrap.min.css">
  </jsp:attribute>

  <jsp:attribute name="inner">
    <h1>Users registered</h1>
    <div class="wrapper">

      <!-- Content Wrapper. Contains page content -->
      <div class="content-wrapper">
        <!-- Main content -->
        <section class="content">
          <div class="row">
            <div class="col-xs-12">
              <table id="users" class="table table-bordered table-striped" style="background-color: white">
                <thead>
                  <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                  </tr>
                </thead>
                <tbody>
                </tbody>
                <tfoot>
                  <tr>
                    <th>Id</th>
                    <th>Username</th>
                    <th>Firstname</th>
                    <th>Lastname</th>
                  </tr>
                </tfoot>
              </table>
            </div>
          </div>
        </section>
      </div>
    </div>
  </jsp:attribute>

  <jsp:attribute name="scripts">
    <script
      src="https://code.jquery.com/jquery-2.2.3.min.js"
      integrity="sha256-a23g1Nt4dtEYOj7bR+vTu7+T8VP13humZFBJNIYoEJo="
    crossorigin="anonymous"></script>
    <script
      src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"
      integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa"
    crossorigin="anonymous"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/jquery.dataTables.min.js"></script>
    <script src="https://cdn.datatables.net/1.10.12/js/dataTables.bootstrap.min.js"></script>
    <script>
      $(function () {
        $('#users').DataTable({
          ajax: {
            url: "${pageContext.request.contextPath}/api/users",
            dataSrc: ""
          },
          columns: [
            {data: "id"},
            {data: "username"},
            {data: "firstname"},
            {data: "lastname"}
          ]
        });
      });
    </script>
  </jsp:attribute>
</t:container>
