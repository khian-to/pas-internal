var autoRefreshIntervalId = null;

$(document).ready(function () {
  $.ajaxSetup({
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json'
    }
  });
  // Extend jQuery to support $.put() and $.delete()
  jQuery.each(["put", "delete"], function (i, method) {
    jQuery[method] = function (url, data, callback, type) {
      if (jQuery.isFunction(data)) {
        type = type || callback;
        callback = data;
        data = undefined;
      }
      return jQuery.ajax({
        url: url,
        type: method,
        dataType: type,
        data: data,
        success: callback
      });
    };
  });

  $("#refreshButton").click(function () {
    refreshTimeTable();
  });
  $("#solveButton").click(function () {
    solve();
  });
  $("#stopSolvingButton").click(function () {
    stopSolving();
  });
  $("#addPatientSubmitButton").click(function () {
    addPatient();
  });
  $("#addBedSubmitButton").click(function () {
    addBed();
  });
  $("#addNightSubmitButton").click(function () {
    addNight();
  });
  $("#addAdmissionSubmitButton").click(function () {
      addAdmission();
    });
$("#addBedRequestSubmitButton").click(function () {
    addBedRequest();
  });

  refreshTimeTable();
});

function refreshTimeTable() {
  $.getJSON("/patientSchedule", function (patientBedAllocation) {
    refreshSolvingButtons(patientBedAllocation.solverStatus != null && patientBedAllocation.solverStatus !== "NOT_SOLVING");
    $("#score").text("Score: " + (patientBedAllocation.score == null ? "?" : patientBedAllocation.score));
    console.log("patientScheduleList: ",patientBedAllocation);
    const patientBedByNight = $("#patientBedByNight");
    patientBedByNight.children().remove();
    const unassignedBedDesignation = $("#unassignedBedDesignation");
    unassignedBedDesignation.children().remove();

    //declarations
    const theadByNight = $("<thead>").appendTo(patientBedByNight);
    const headerRowByNight = $("<tr>").appendTo(theadByNight);
    headerRowByNight.append($("<th>Bed</th>"));
    $.each(patientBedAllocation.nightList, (index, night) => {
      headerRowByNight
        .append($("<th/>")
          .append($("<span/>").text(night.label))
          .append($(`<button type="button" class="ml-2 mb-1 btn btn-light btn-sm p-1"/>`)
            .append($(`<small class="fas fa-trash"/>`)
            ).click(() => deleteNight(night))));
    });

    const tbodyByNight = $("<tbody>").appendTo(patientBedByNight);
    $.each(patientBedAllocation.bedList, (index, bed) => {
      const rowByNight = $("<tr>").appendTo(tbodyByNight);
      rowByNight
        .append($(`<th class="align-middle"/>`)
          .append($("<span/>").text(`Bed${bed.name }`)
            .append($(`<button type="button" class="ml-2 mb-1 btn btn-light btn-sm p-1"/>`)
              .append($(`<small class="fas fa-trash"/>`)
              ).click(() => deleteBed(bed)))));
      $.each(patientBedAllocation.nightList, (index, night) => {
//      console.log(`bed${bed.id}night${night.id}`)
        rowByNight.append($("<td/>").prop("id", `bed${bed.id}night${night.id}`));
      });

    });

    $.each(patientBedAllocation.bedDesignationList, (index, bedDesignation) => {
      const color = pickColor(bedDesignation.admissionPart.patient.name);
      const bedDesignationElementWithoutDelete = $(`<div class="card lesson" style="background-color: ${color}"/>`)
        .append($(`<div class="card-body p-2"/>`)
          .append($(`<h5 class="card-title mb-1"/>`).text(bedDesignation.admissionPart.patient.name))
          .append($(`<p class="card-text ml-2 mb-1"/>`)
            .append($(`<em/>`).text(`${bedDesignation.admissionPart.patient.age} y/o`)))
          .append($(`<small class="ml-2 mt-1 card-text text-muted align-bottom float-right"/>`).text(bedDesignation.admissionPart.id))
          .append($(`<p class="card-text ml-2"/>`).text(bedDesignation.admissionPart.patient.gender)));
      const bedDesignationElement = bedDesignationElementWithoutDelete.clone();
      bedDesignationElement.find(".card-body").prepend(
        $(`<button type="button" class="ml-2 btn btn-light btn-sm p-1 float-right"/>`)
          .append($(`<small class="fas fa-trash"/>`)
          ).click(() => deleteBedDesignation(bedDesignation))
      );

      //Bed Designation
      unassignedBedDesignation.append(bedDesignationElement);
      if (bedDesignation.bed == null || bedDesignation.admissionPart.firstNight.id == null) {
        unassignedBedDesignation.append(bedDesignationElement);
      } else {
        $(`#bed${bedDesignation.bed.id}night${bedDesignation.admissionPart.firstNight.id}`).append(bedDesignationElement);
      }
    });
  });
}

function convertToId(str) {
  // Base64 encoding without padding to avoid XSS
  return btoa(str).replace(/=/g, "");
}

function solve() {
  $.post("/patientSchedule/solve", function () {
    refreshSolvingButtons(true);
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Start solving failed.", xhr);
  });
}

function refreshSolvingButtons(solving) {
  if (solving) {
    $("#solveButton").hide();
    $("#stopSolvingButton").show();
    if (autoRefreshIntervalId == null) {
      autoRefreshIntervalId = setInterval(refreshTimeTable, 2000);
    }
  } else {
    $("#solveButton").show();
    $("#stopSolvingButton").hide();
    if (autoRefreshIntervalId != null) {
      clearInterval(autoRefreshIntervalId);
      autoRefreshIntervalId = null;
    }
  }
}

function stopSolving() {
  $.post("/timeTable/stopSolving", function () {
    refreshSolvingButtons(false);
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Stop solving failed.", xhr);
  });
}

function addPatient() {
console.log("adding patient")
  var patient_name = $("#patient-name").val().trim();
  $.post("/patient", JSON.stringify({
    "name": patient_name,
    "gender": $("#gender").val().trim(),
    "age": $("#age").val().trim(),
    "preferredMaximumRoomCapacity": $("#prefMaxRoomCapacity").val().trim()
  }), function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Adding patient(" + patient_name + ") failed.", xhr);
  });
  $('#patientDialog').modal('toggle');
}

function deleteLesson(lesson) {
  $.delete("/lessons/" + lesson.id, function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Deleting lesson (" + lesson.name + ") failed.", xhr);
  });
}

function addBed() {
  $.post("/bed", JSON.stringify({
    "name": $("#bed_name").val().trim(),
    "room_id": $("#room_id").val().trim(),
    "indexInRoom": $("#index_in_room").val().trim()
  }), function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Adding bed failed.", xhr);
  });
  $('#bedDialog').modal('toggle');
}

function deleteBed(bed) {
  $.delete("/bed/" + bed.id, function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Deleting bed (" + bed.name + ") failed.", xhr);
  });
}

function addNight() {
  var index = $("#night_index").val().trim();
  $.post("/night", JSON.stringify({
    "index": index
  }), function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Adding night (" + name + ") failed.", xhr);
  });
  $("#nightDialog").modal('toggle');
}
function addAdmission() {
  $.post("/admissionPart", JSON.stringify({
    "patient":{
        "id":$("#patient-id").val().trim()
    },
    "firstNight":{
        "id":1
    },
    "lastNight":{
            "id":$("#night-count").val().trim()
    },
    "specialism":{
        "id":$("#specialism-id").val().trim()
    }
  }), function (response) {

    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Adding Admission (" + name + ") failed.", xhr);
  });
  $("#admissionDialog").modal('toggle');
}
function addBedRequest() {
  $.post("/bedDesignation", JSON.stringify({
    "admissionPart": {
        "id":$("#admission-id").val().trim()
    }
  }), function (response) {

    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Adding Bed Request (" + name + ") failed.", xhr);
  });
  $("#bedRequestDialog").modal('toggle');
}
function deleteNight(night) {
  $.delete("/night/" + night.id, function () {
    refreshTimeTable();
  }).fail(function (xhr, ajaxOptions, thrownError) {
    showError("Deleting night (" + room.name + ") failed.", xhr);
  });
}

function showError(title, xhr) {
  const serverErrorMessage = !xhr.responseJSON ? `${xhr.status}: ${xhr.statusText}` : xhr.responseJSON.message;
  console.error(title + "\n" + serverErrorMessage);
  const notification = $(`<div class="toast" role="alert" aria-live="assertive" aria-atomic="true" style="min-width: 30rem"/>`)
    .append($(`<div class="toast-header bg-danger">
                 <strong class="mr-auto text-dark">Error</strong>
                 <button type="button" class="ml-2 mb-1 close" data-dismiss="toast" aria-label="Close">
                   <span aria-hidden="true">&times;</span>
                 </button>
               </div>`))
    .append($(`<div class="toast-body"/>`)
      .append($(`<p/>`).text(title))
      .append($(`<pre/>`)
        .append($(`<code/>`).text(serverErrorMessage))
      )
    );
  $("#notificationPanel").append(notification);
  notification.toast({delay: 30000});
  notification.toast('show');
}

// ****************************************************************************
// TangoColorFactory
// ****************************************************************************

const SEQUENCE_1 = [0x8AE234, 0xFCE94F, 0x729FCF, 0xE9B96E, 0xAD7FA8];
const SEQUENCE_2 = [0x73D216, 0xEDD400, 0x3465A4, 0xC17D11, 0x75507B];

var colorMap = new Map;
var nextColorCount = 0;

function pickColor(object) {
  let color = colorMap[object];
  if (color !== undefined) {
    return color;
  }
  color = nextColor();
  colorMap[object] = color;
  return color;
}

function nextColor() {
  let color;
  let colorIndex = nextColorCount % SEQUENCE_1.length;
  let shadeIndex = Math.floor(nextColorCount / SEQUENCE_1.length);
  if (shadeIndex === 0) {
    color = SEQUENCE_1[colorIndex];
  } else if (shadeIndex === 1) {
    color = SEQUENCE_2[colorIndex];
  } else {
    shadeIndex -= 3;
    let floorColor = SEQUENCE_2[colorIndex];
    let ceilColor = SEQUENCE_1[colorIndex];
    let base = Math.floor((shadeIndex / 2) + 1);
    let divisor = 2;
    while (base >= divisor) {
      divisor *= 2;
    }
    base = (base * 2) - divisor + 1;
    let shadePercentage = base / divisor;
    color = buildPercentageColor(floorColor, ceilColor, shadePercentage);
  }
  nextColorCount++;
  return "#" + color.toString(16);
}

function buildPercentageColor(floorColor, ceilColor, shadePercentage) {
  let red = (floorColor & 0xFF0000) + Math.floor(shadePercentage * ((ceilColor & 0xFF0000) - (floorColor & 0xFF0000))) & 0xFF0000;
  let green = (floorColor & 0x00FF00) + Math.floor(shadePercentage * ((ceilColor & 0x00FF00) - (floorColor & 0x00FF00))) & 0x00FF00;
  let blue = (floorColor & 0x0000FF) + Math.floor(shadePercentage * ((ceilColor & 0x0000FF) - (floorColor & 0x0000FF))) & 0x0000FF;
  return red | green | blue;
}



