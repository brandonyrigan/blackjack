package com.pcbuilder.menus;

import static com.pcbuilder.menus.IDecorate.*;
import com.pcbuilder.build.Build;
import com.pcbuilder.customer.Customer;
import com.pcbuilder.session.Session;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;

public class MainMenu extends Menu{


    protected Session session;
    protected SubmenuMap submenuMap;
    protected String confirmSelection;
    protected int selection;
    public int componentCount;
    protected String[] customerInfo;
    protected Map<String, Double> currentBuildPrices = new HashMap<>();


//TODO[ ] - Refactor: Constructor Injection > Setter Injection
    public MainMenu( Session session, String... customerInfo ){
        setSession( session );
        setCustomerInfo( customerInfo );
        setComponentCount(0);
        SubmenuMap submenuMap = new SubmenuMap(getSession());
        setSubmenuMap( submenuMap ); // Reactor to use Constructor Injection
    }


    @Override
    public void renderOwnMenu() {
        createSubmenus();
        do{
            mainMenuHeader();
            renderCurrentSessionBuild();
            updateUserSelection();
            Menu targetSubmenu = submenuMap.runSubmenu( getSelection() );
//TODO[]- FIX: NullPointerException, need an Optional
            targetSubmenu.renderOwnMenu();

        } while( getSelection() != 0 );
    }


//  Menu Methods
    protected void createSubmenus(){
        SubmenuMap submenuMap = getSubmenuMap();
        submenuMap.createSubmenus();
    }
    private void mainMenuHeader(){
        String firstName = session.getCustomer().getFirstName();
        String lastName = session.getCustomer().getLastName();
        System.out.println( RENDER_BAR.getDecoration() );
        System.out.println( " Customer:  " + firstName + ", "+ lastName );
        System.out.println(" MENU: " +
                "  [1] PC Builder " +
                "  [2] Shopping Cart " +
                "  [3] View Order " +
                "  [4] Purchase"+
                "  [5] Update Customer Information "
        );
    }
    protected void updateUserSelection(){
        Scanner scanner = new Scanner(System.in);
        setSelection( Integer.parseInt( scanner.next() ));
    }
    protected void updateConfirmSelection(){
        Scanner scanner = new Scanner(System.in);
        setConfirmSelection( scanner.nextLine() );
    }

//  Build Methods
    protected void createBuild( ) {
        Session session = getSession();
        Build build = session.composeBuild( session );
        session.addBuildToCart(build);
    }
    protected void renderCurrentSessionBuild(){
        Session session = getSession();
        Map<String, String> sessionBuild = session.getSessionBuild();
        String categoryName = "";
        String componentName = "";
        String[] componentInfo = new String[]{};

        System.out.println( "Current Build " );
        for( Map.Entry<String, String> entries : sessionBuild.entrySet() ){
            categoryName = entries.getKey();
            componentInfo = entries.getValue().split(",");
            componentName = componentInfo[0];
            System.out.println("   "+ categoryName +": "+ componentName );
        };

        System.out.println("Build Progress: " + getComponentCount() + "/8");
        if (!currentBuildPrices.isEmpty()) {
            System.out.println("Current Total Build Price: $" + calculateCurrentTotalBuildPrice());
        }
        System.out.println( RENDER_BAR.getDecoration() );

    }
    protected void displayBuildCompleteMessage() {
        System.out.println( RENDER_BAR.getDecoration());
        System.out.println("Build is complete! All 8 components have been selected. Please go to shopping cart to complete order.");
        System.out.println( RENDER_BAR.getDecoration() );
    }
    private String calculateCurrentTotalBuildPrice() {
        Double total = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");
        for (Map.Entry<String, Double> price : currentBuildPrices.entrySet()) {
            total += price.getValue();
        }
        return df.format(total);
    }

//    Accessor Methods
    public SubmenuMap getSubmenuMap() { return submenuMap; }
    public void setSubmenuMap(SubmenuMap submenuMap) { this.submenuMap = submenuMap; }
    public Session getSession() { return session; }
    public void setSession(Session session) { this.session = session; }
    public int getSelection() { return selection; }
    public void setSelection(int selection) { this.selection = selection; }
    public String getConfirmSelection() { return confirmSelection; }
    public void setConfirmSelection(String confirmSelection) { this.confirmSelection = confirmSelection; }
    public String[] getCustomerInfo() { return customerInfo; }
    public void setCustomerInfo(String[] customerInfo) { this.customerInfo = customerInfo; }
    public int getComponentCount() { return componentCount; }
    public void setComponentCount( int componentCount ) { this.componentCount = componentCount; }
}