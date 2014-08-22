<div class="report" id="${report?.id}">
   <div>Report Generated For: ${report?.enterprise?.name}</div>
    <div>Title: ${report?.title}</div>
    <div>Report Type: ${report?.reportType.getFullTitle()}</div>
   <div>Run Date: <g:formatDate format="yyyy-MM-dd" date="${new Date()}"/></div>
   <div>Report Date Range: <g:formatDate format="yyyy-MM-dd" date="${report.startDate}"/> to <g:formatDate format="yyyy-MM-dd" date="${report.endDate}"/></div>
   
</div>
