package ru.simbirsoft.warehouse_management.util.impl;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Component;
import ru.simbirsoft.warehouse_management.model.Item;
import ru.simbirsoft.warehouse_management.model.Shop;
import ru.simbirsoft.warehouse_management.model.Warehouse;
import ru.simbirsoft.warehouse_management.util.PdfReportUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Map;

@Component
public class ItextPdfReportUtil implements PdfReportUtil {

  private static final Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);

  @Override
  public void generateSalesReportForShops(
      Map<Shop, Map<Item, Integer>> itemCountsForShops, Document document) {
    for (Map.Entry<Shop, Map<Item, Integer>> shopItems : itemCountsForShops.entrySet()) {
      generateSalesReportForOneShop(shopItems.getKey(), shopItems.getValue(), document);
    }
  }

  @Override
  public void generateSalesReportForOneShop(
      Shop shop, Map<Item, Integer> itemCounts, Document document) {
    try {
      double totalCost = 0.0;
      Paragraph purpose = new Paragraph(shop.getName());
      purpose.setSpacingAfter(10);
      document.add(purpose);
      PdfPTable pdfPTable = new PdfPTable(6);
      pdfPTable.setWidthPercentage(80);
      pdfPTable.setWidths(new int[] {1, 5, 2, 2, 2, 3});

      pdfPTable.addCell(generatePdfHeaderCell("id"));
      pdfPTable.addCell(generatePdfHeaderCell("Name"));
      pdfPTable.addCell(generatePdfHeaderCell("Code"));
      pdfPTable.addCell(generatePdfHeaderCell("Count"));
      pdfPTable.addCell(generatePdfHeaderCell("Price"));
      pdfPTable.addCell(generatePdfHeaderCell("Total price"));

      for (Map.Entry<Item, Integer> itemCount : itemCounts.entrySet()) {

        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getKey().getId())));
        pdfPTable.addCell(generatePdfTableCell(itemCount.getKey().getName()));
        pdfPTable.addCell(generatePdfTableCell(itemCount.getKey().getCode()));
        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getValue())));
        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getKey().getPrice())));
        pdfPTable.addCell(
            generatePdfTableCell(
                String.valueOf(itemCount.getKey().getPrice() * itemCount.getValue())));
        totalCost += itemCount.getKey().getPrice() * itemCount.getValue();
      }
      document.add(pdfPTable);
      Paragraph totalPrice = new Paragraph("total revenue is " + totalCost);
      totalPrice.setAlignment(Element.ALIGN_RIGHT);
      totalPrice.setSpacingAfter(30);
      document.add(totalPrice);
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
  }

  @Override
  public void generateWriteoffReportForWarehouses(
      Map<Warehouse, Map<Item, Integer>> itemCountsForWarehouse, Document document) {
    for (Map.Entry<Warehouse, Map<Item, Integer>> warehouseWriteoffedItems :
        itemCountsForWarehouse.entrySet()) {
      generateWriteoffReportForOneWarehouse(
          warehouseWriteoffedItems.getKey(), warehouseWriteoffedItems.getValue(), document);
    }
  }

  @Override
  public void generateWriteoffReportForOneWarehouse(
      Warehouse warehouse, Map<Item, Integer> itemCounts, Document document) {
    try {
      Paragraph purpose = new Paragraph(warehouse.getAddress());
      purpose.setSpacingAfter(10);
      document.add(purpose);
      PdfPTable pdfPTable = new PdfPTable(5);
      pdfPTable.setWidthPercentage(80);
      pdfPTable.setWidths(new int[] {1, 5, 3, 2, 2});

      // generatin head of table
      pdfPTable.addCell(generatePdfHeaderCell("id"));
      pdfPTable.addCell(generatePdfHeaderCell("Name"));
      pdfPTable.addCell(generatePdfHeaderCell("Code"));
      pdfPTable.addCell(generatePdfHeaderCell("Count"));
      pdfPTable.addCell(generatePdfHeaderCell("Price"));

      for (Map.Entry<Item, Integer> itemCount : itemCounts.entrySet()) {
        // generate table entry
        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getKey().getId())));
        pdfPTable.addCell(generatePdfTableCell(itemCount.getKey().getName()));
        pdfPTable.addCell(generatePdfTableCell(itemCount.getKey().getCode()));
        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getValue())));
        pdfPTable.addCell(generatePdfTableCell(String.valueOf(itemCount.getKey().getPrice())));
        pdfPTable.setSpacingAfter(30);
      }
      document.add(pdfPTable);
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
  }

  @Override
  public void generateRevenueReportForShops(Map<Shop, Double> shopRevenue, Document document) {
    for (Map.Entry<Shop, Double> entry : shopRevenue.entrySet()) {
      generateRevenueReportForOneShop(entry.getKey(), entry.getValue(), document);
    }
  }

  @Override
  public void generateRevenueReportForOneShop(Shop shop, Double revenue, Document document) {
    try {
      Paragraph purpose = new Paragraph("Average Revenue for " + shop.getName());
      purpose.setSpacingAfter(10);
      document.add(purpose);
      PdfPTable pdfPTable = new PdfPTable(2);
      pdfPTable.setWidthPercentage(80);
      pdfPTable.setWidths(new int[] {5, 5});

      // generatin head of table
      pdfPTable.addCell(generatePdfHeaderCell("Shop name"));
      pdfPTable.addCell(generatePdfHeaderCell("Revenue"));

      // genearin row
      pdfPTable.addCell(generatePdfTableCell(shop.getName()));
      pdfPTable.addCell(generatePdfTableCell(String.valueOf(revenue)));
      pdfPTable.setSpacingAfter(15);

      document.add(pdfPTable);
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
  }

  @Override
  public void generateAverageCheckForShops(Map<Shop, Double> shopAverages, Document document) {
    for (Map.Entry<Shop, Double> entry : shopAverages.entrySet()) {
      generateAverageCheckForShop(entry.getKey(), entry.getValue(), document);
    }
  }

  @Override
  public void generateAverageCheckForShop(Shop shop, Double average, Document document) {
    try {
      Paragraph purpose = new Paragraph("Average Check for " + shop.getName());
      purpose.setSpacingAfter(10);
      document.add(purpose);
      PdfPTable pdfPTable = new PdfPTable(2);
      pdfPTable.setWidthPercentage(80);
      pdfPTable.setWidths(new int[] {5, 5});

      // generatin head of table
      pdfPTable.addCell(generatePdfHeaderCell("Shop name"));
      pdfPTable.addCell(generatePdfHeaderCell("Average"));

      // genearin row
      pdfPTable.addCell(generatePdfTableCell(shop.getName()));
      pdfPTable.addCell(generatePdfTableCell(String.valueOf(average)));
      pdfPTable.setSpacingAfter(15);

      document.add(pdfPTable);
    } catch (DocumentException e) {
      throw new IllegalStateException("Can not generate pdf document");
    }
  }

  private PdfPCell generatePdfHeaderCell(String text) {
    PdfPCell hcell = new PdfPCell(new Phrase(text, headFont));
    hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return hcell;
  }

  private PdfPCell generatePdfTableCell(String text) {
    PdfPCell cell = new PdfPCell(new Phrase(text));
    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
    return cell;
  }
}
