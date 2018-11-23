package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ImageView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.ui.confirm.ShowConfirmActivity;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

/**
 * Created by vitinhHienAnh on 30-10-18.
 */

public class NightRaceFragment extends Fragment {
    private SharePreferenceUtils sharePreferenceUtils;
    private WebView webviewRule;
    public static NightRaceFragment newInstance() {
        NightRaceFragment fragmentFirst = new NightRaceFragment();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_night_race , container , false);
        webviewRule = view.findViewById(R.id.webviewRule);
        sharePreferenceUtils = new SharePreferenceUtils(getActivity());
        String language = sharePreferenceUtils.getLanguage();
        if ("kh".equals(language)){
            String unencodedHtml = "<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                    "        " +
                    "</head><body style='background-color:#B40034;'>" +
                    "            <style>" +
                    ".container p" +
                    "{" +
                    "color:white;" +
                    "            text-align: left;" +
                    "}" +
                    ".container " +
                    "{" +
                    "color:white;" +
                    "            font-family:\"Helvetica Neue\",Helvetica,Arial,sans-serif;" +
                    "            text-align: left;" +
                    "}" +
                    ".classP" +
                    "{" +
                    " text-transform: uppercase;" +
                    "    font-size: 17px;" +
                    "    text-decoration: none;" +
                    "    border-bottom: 1px solid white;" +
                    "    color:#8dda84 !important;" +
                    "}" +
                    "li::before {" +
                    "     content: \"• \";" +
                    "    color: #3fc9df;" +
                    "    font-size: 18px;" +
                    "}" +
                    "ul {" +
                    "  list-style: none;" +
                    "}" +
                    "a:hover, a:focus " +
                    "{" +
                    "color:black;" +
                    "}" +
                    "a{" +
                    "color: #8dda84" +
                    "}" +
                    "</style>" +
                    "<div class=\"container\" style=\"text-align: justify;\">" +
                    "<p class='classP'>ការប្រកួត នឹងបើកទូលាយសម្រាប់រាល់អតិថិជនចាស់ និងថ្មីទាំងអស់ របស់ក្រុមហ៊ុន ទូរសព្ទចល័ត មិត្តហ្វូន ទូទាំងប្រទេសកម្ពុជា</p>" +
                    "<p class='classP'>ពេលវេលា: </p>" +
                    "<div>" +
                    " <ul> " +
                    " <li> សំបុត្រនឹងត្រូវផ្តល់ជូន ចាប់ពីថ្ងៃទី ១៧ខែវិច្ឆិកា ដល់ថ្ងៃទី ១៦ខែធ្នូ ឆ្នាំ ២០១៨" +
                    " </li>" +
                    " <li>អតិថិជន អាចយកសំបុត្រទៅស្កេន QR កូដនៅ showrooms បស់មិត្តហ្វូន ដើម្បីប្តូរយក សម្ភារៈ ចាប់ពីថ្ងៃទី ១៦ ដល់ថ្ងៃទី ២១ ខែធ្នូ ឆ្នាំ ២០១៨ ។" +
                    " </li>" +
                    " <li>ការប្រកួតនឹងត្រូវ ប្រព្រឹត្តនៅថ្ងៃទី ២២ ខែធ្នូ ឆ្នាំ ២០១៨ ចាប់ពីម៉ោង ៤រសៀល\u200B រហូតដល់ម៉ោង ១០យប់ នៅកីឡាដ្ឋានជាតិ អូឡាំពិច ។" +
                    " </li>" +
                    " </ul>" +
                    "</div>" +
                    "<p class='classP'>" +
                    "ប្រភេទសំបុត្រ និងការចុះឈ្មោះ" +
                    "</p>" +
                    "<p>ប្រភេទសំបុត្រ:</p>" +
                    "<div>" +
                    " <ul> " +
                    " <li><strong> VIP 1: </strong>ទទួលបានការចូលរត់ប្រណាំង + អាវយឺត ១ + មើលការប្រគុំតន្ត្រី <strong>សំបុត្រមានប្រហែល ៣០០០០ សំបុត្រ</strong>" +
                    "   ដើម្បីចុះឈ្មោះ អតិថិជនគ្រាន់តែ<strong>ទាញយក និងដម្លើងកម្មវិធី Mochaដោយចូលទៅកាន់គេហទំព័រ </strong>: <a href=\"http://mocha.com.kh/\" target=\"_blank\">http://mocha.com.kh/</a> <strong>ឬ</strong> បញ្ចូលលុយចាប់ពី 2$/ថ្ងៃ តាមរយៈសន្លឹកកាតកោស អ៊ីម៉ាន់នី ឬគណនេយ្យធនាគារ ។" +
                    " </li>" +
                    " </ul>" +
                    " " +
                    " <ul> " +
                    " <li> <strong> VIP 2: </strong>ទទួលបានការចូលរត់ប្រណាំង + អាវយឺត១ + សម្ភារៈពេញមួយឈុត (ដង្កៀបសក់ វ៉ែនតា ខ្សែក ដងពន្លឺសម្រាប់អបអរ)<strong> សំបុត្រមានប្រហែល ១០០០០ សំបុត្រ</strong>" +
                    "   ដើម្បីចុះឈ្មោះ អតិថិជនគ្រាន់តែ បញ្ចូលលុយចាប់ពី 5$/ថ្ងៃ តាមរយៈសន្លឹកកាតកោស អ៊ីម៉ាន់នី ឬគណនេយ្យធនាគារ ។" +
                    " </li>" +
                    " </ul>" +
                    " " +
                    " <ul> " +
                    " <li><strong>VIP 3: </strong>ទទួលបានការចូលរត់ប្រណាំង + អាវយឺត១ + សម្ភារៈពេញមួយឈុត + អាវពន្លឺចំនួន ១ ។ <strong>សំបុត្រមានប្រហែល ១០០០ សំបុត្រ</strong>" +
                    "   ដើម្បីចុះឈ្មោះ អតិថិជនគ្រាន់តែ បញ្ចូលលុយចាប់ពី 10$/ថ្ងៃ តាមរយៈសន្លឹកកាតកោស អ៊ីម៉ាន់នី ឬគណនេយ្យធនាគារ ។" +
                    " </li>" +
                    " </ul>" +
                    "</div>" +
                    "<p>ដើម្បីទទួលបានសំបុត្រ</p>" +
                    "  <ul> " +
                    " <li>នៅពេលដែលអតិថិជន ទាញយក និងតម្លើងកម្មវិធី Mocha និងបញ្ចូលលុយទៅក្នុងទូរសព្ទ, នោះនឹងមានសារមួយ ផ្ញើចូលទៅក្នុងទូរសព្ទរបស់អតិថិជន ដែលស្ថិតនៅក្នុងប្រអប់សារ ឬនៅក្នុងកម្មវិធី Mocha ។</li>" +
                    "  <li>លោកអ្នកនឹងទទួលបាន តំណរ <a href='http://nightrace.metfone.com.kh'> http://nightrace.metfone.com.kh </a>នៅក្នុងសារនោះ , លោកអ្នកគ្រាន់តែចុចលើតណភា្ជប់នោះ ហើយចុចលើប៊ូតុង Register ដើម្បីបញ្ជាក់ពីការទទួលបានសំបុត្រ e-tickets ដើម្បីចូលរួមប្រកួត ។</li>" +
                    " <li>ចំនួនសំបុត្រ ត្រូវបានកំណត់សម្រាប់អតិថិជន ទៅតាមប្រភេទនៃសំបុត្រនីមួយៗ ដូចដែលបានរៀបរាប់នៅខាងលើ ហើយអតិថិជនម្នាក់ អាចទទួលបានសំបុត្រតែមួយប៉ុណ្ណោះ ។</li>" +
                    " " +
                    " </ul>" +
                    " <p>ដើម្បីចូលទៅកាន់ទីលានរត់ប្រណាំង</p>" +
                    "  <ul> " +
                    " <li>នៅពេលដែលអតិថិជន ទៅដល់ កីឡាដ្ឋានជាតិ អូឡាំពិច, លោកអ្នកគ្រាន់តែភ្ជាប់មកជាមួយសំបុ\u200Bត្រ ដែលទទួលបាន ហើយធ្វើការស្កេនជាមួយនឹង បុគ្គលិករបស់ក្រុមហ៊ុនមិត្តហ្វូន ជាការស្រេច ។ ចំណាំ៖ e-tickets គឺជាប្រភេទសំបុត្រដែលមានផ្ទុក QR កូដសម្រាប់ស្កេន ។ អតិថិជន អាចទទួលបានសំបុត្រនេះតាមរយៈ ការចុះឈ្មោះ Online ហើយគ្រាន់តែធ្វើការថត ឬព្រីនស្គ្រីនសំបុត្រនោះទុក ដើម្បីយកមកស្កេន សម្រាប់មានសិទ្ធិចូលរួម ប្រកួតជាមួយកម្មវិធី ។</li>" +
                    " </ul>" +
                    "<p>&nbsp;</p>" +
                    "<p class='classP'>" +
                    "The giveaways </p>" +
                    "<p>ការយកសំបុត្រមកស្កេន ដើម្បីទទួលបានសម្ភារៈ នៅ Showroom</p>" +
                    "  <ul> " +
                    " <li>សម្ភារៈ នឹងត្រូវផ្តល់ជូន ឲ្យអតិថិជនទាំងអស់ ដែលបានចូលរួម ។ ការផ្តល់ជូនសម្ភារៈ នឹងត្រូវផ្អែកទៅលើប្រភេទនៃសំបុត្រ e-tickets នីមួយៗ ។</li>" +
                    "  <li>អតិថិជន គ្រាន់តែយកសំបុត្រ e-tickets ដែលទទួលបាន\u200B ទៅកាន់ Showroom របស់មិត្តហ្វូន ដើម្បីធ្វើការស្កេន QR កូដ ដែលមាននៅលើសំបុត្រ ដើម្បីទទួលបានសម្ភារៈ សម្រាប់ចូលរួមប្រកួត ជាមួយកម្មវិធី ។</li>" +
                    " <li>ការផ្តល់ជូនសម្ភារៈ នឹងត្រូវចាប់ផ្តើមពី ថ្ងៃទី ១៦ ដល់ថ្ងៃទី ២១ខែធ្នូ ឆ្នាំ ២០១៨ ។</li>" +
                    " <li>ពេលមកស្កេនសំបុត្រនៅ Showroom សូមភ្ជាប់មកជាមួយនូវ អត្តសញ្ញាណប័ណ្ណ លិខិតឆ្លងដែន ឬឯកសារដែលមានប្រសិទ្ធិភាពដទៃទៀត មកជាមួយ ។</li>" +
                    " </ul>" +
                    "<p>តើអាចយកសំបុត្រ e-tickets ទៅស្កេន ដើម្បីទទួលបានសម្ភារៈ នៅទីណាខ្លះ ?</p>" +
                    " <ul>" +
                    "<li><strong>Showroom</strong><strong><em> មិត្តហ្វូន PNP05 - ទឹកថ្លា</em></strong></li>" +
                    "<p>អាសយដ្ឋាន:  ផ្ទះលេខ 2AB, ផ្លូវឬស្សី សង្កាត់ទឹកថ្លា, ខណ្ឌសែនសុខ រាជធានីភ្នំពេញ</p>" +
                    "<p>ទូរសព្ទ: 0236200170/0236200166</p>" +
                    "<li><strong>Showroom</strong><strong><em> មិត្តហ្វូន PNP08-ជ្រោយចង្វារ</em></strong></li>" +
                    "<p>អាសយដ្ឋាន: ផ្ទះលេខ A11&amp;A13, E0, E1, E2, ផ្លូវលេខ ៧០ សង្កាត់ស្រះចក ខណ្ឌដូនពេញ រាជធានីភ្នំពេញ</p>" +
                    "<p>ទូរសព្ទ: 0236208168 , 0236200169</p>" +
                    "<li><strong>Showroom</strong><strong><em> មិត្តហ្វូន PNP11-ទួលស្វាយព្រៃ</em></strong></li>" +
                    "<p>អាសយដ្ឋាន: ផ្ទះលេខ 267, 269, 271E0, 267E1, ផ្លូវម៉ៅសេងទុង, សង្កាត់ទួលស្វាយព្រៃ ២ , ខណ្ឌចំការមន, រាជធានីភ្នំពេញ</p>" +
                    "<p>ទូរសព្ទ: 0236200174, 0236216168</p>" +
                    "" +
                    "<li><strong>Showroom</strong><strong><em> មិត្តហ្វូន PNP28 – កោះពេជ្រ</em></strong><p></p>" +
                    "<p>អាសយដ្ឋាន: ទន្លេបាសាក់ កោះពេជ្រ, រាជធានីភ្នំពេញ</p></li>" +
                    "</ul>" +
                    "<p><strong>&nbsp;</strong></p>" +
                    "<p class='classP'>" +
                    "លក្ខខណ្ឌផ្សេងៗ" +
                    "</p>" +
                    " <ul> " +
                    " <li>ការផ្សាយពាណិជ្ជកម្ម និងព័ត៍មានលំអិតអំពីការប្រកួត នឹងត្រូវ\u200Bបានផ្សព្វផ្សាយ នៅលើបណ្តាញសង្គម Facebook Fanpage របស់ក្រុមហ៊ុនមិត្តហ្វូន,, ទូរទស្សន៍របស់ហង្សមាស និងប្រព័ន្ធផ្សព្វផ្សាយផ្សេងៗទៀត ។</li>" +
                    "  <li>កម្មវិធី Night Race នឹងត្រូវចាក់ផ្សាយ បន្តផ្ទាល់នៅលើកញ្ចក់ទូរទស្សន៍របស់ហង្សមាស និង  Facebook Fanpage របស់ក្រុមហ៊ុនមិត្តហ្វូន ។</li>" +
                    " <li>ដោយសារតែសំបុត្រ e-tickets និង ការផ្តល់ជូនសម្ភារៈ មានចំនួនកំណត់ មិត្តហ្វូនអាចនឹងបញ្ចប់ការផ្តល់ជូននេះមុនកំណត់ ដោយនឹងមានការផ្តល់ជូនដំណឹងទុកជាមុន នៅពេលដែលសំបុត្រ e-tickets និងសម្ភារៈ ត្រូវផ្តល់ជូនអស់ ។ </li>" +
                    " <li>ការចូលរួមកម្មវិធី Night Race អ្នកចូលរួម ត្រូវតែយល់ព្រមអំពីលក្ខខណ្ឌដែលបានកំណត់, រាល់ការប្រព្រឹត្តកំហុសណាមួយ ក្រុមហ៊ុនមិត្តហ្វូន សូមរក្សាសិទ្ធិក្នុងការកែប្រែ នៃការផ្តល់ជូនសម្ភារៈ ។</li>" +
                    " <li>ក្រុមហ៊ុនមិត្តហ្វូន សូមរក្សាសិទ្ធិលុបចោល បញ្ចប់ ឬពន្យាពេលនៃកម្មវិធី ដោយគ្មានការជូនដំណឹងទុកជាមុន ។ ដើម្បីការជៀសវាងនៃការលុបចោល ការបញ្ចប់ ឬការពន្យាពេលណាមយយដោយមិត្តហ្វូននៃកម្មវិធី Night Race ដោយមិនអនុញ្ញាតឲ្យអ្នកចូលរួម ធ្វើការប្តឹងតវ៉ា ឬទាមទារសំណងពីមិត្តហ្វូនចំពោះការខាតបង់ ឬខូចខាតណាមួយ ដែលបានប្រព្រឹត្តដោយអ្នកចូលរួមផ្ទាល់ ឬដោយប្រយោល ដែលជាលទ្ធផល នៃការលុបចោល បញ្ចប់ ឬការពន្យាពេល ។</li>" +
                    " </ul>" +
                    "</div>" +
                    "                " +
                    "            </div>" +
                    "            " +
                    "</body></html>";
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                    Base64.NO_PADDING);
            webviewRule.loadData(encodedHtml, "text/html", "base64");
        }else {
            String unencodedHtml = "<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                    "" +
                    "        " +
                    "" +
                    "</head><body style='background-color:#B40034;'>" +
                    "" +
                    "            <style>" +
                    "" +
                    ".container p" +
                    "" +
                    "{" +
                    "" +
                    "color:white;" +
                    "" +
                    "            text-align: left;" +
                    "" +
                    "}" +
                    "" +
                    ".container " +
                    "" +
                    "{" +
                    "" +
                    "color:white;" +
                    "" +
                    "            font-family:\"Helvetica Neue\",Helvetica,Arial,sans-serif;" +
                    "" +
                    "            text-align: left;" +
                    "" +
                    "}" +
                    "" +
                    ".classP" +
                    "" +
                    "{" +
                    "" +
                    " text-transform: uppercase;" +
                    "" +
                    "    font-size: 17px;" +
                    "" +
                    "    text-decoration: none;" +
                    "" +
                    "    border-bottom: 1px solid white;" +
                    "" +
                    "    color:#8dda84 !important;" +
                    "" +
                    "}" +
                    "" +
                    "li::before {" +
                    "" +
                    "     content: \"• \";" +
                    "" +
                    "    color: #3fc9df;" +
                    "" +
                    "    font-size: 18px;" +
                    "" +
                    "}" +
                    "" +
                    "ul {" +
                    "" +
                    "  list-style: none;" +
                    "" +
                    "}" +
                    "" +
                    "a:hover, a:focus " +
                    "" +
                    "{" +
                    "" +
                    "color:black;" +
                    "" +
                    "}" +
                    "" +
                    "a{" +
                    "" +
                    "color: #8dda84" +
                    "" +
                    "}" +
                    "" +
                    "</style>" +
                    "" +
                    "<div class=\"container\" style=\"text-align: justify;\">" +
                    "" +
                    "<p class='classP'>The race is open to all existing and new mobile customers of Metfone in Cambodia</p>" +
                    "" +
                    "<p class='classP'>Time: </p>" +
                    "" +
                    "<div>" +
                    "" +
                    " <ul> " +
                    "" +
                    " <li> Ticket will be issued from 17th November 2018 to 16th December 2018." +
                    "" +
                    " </li>" +
                    "" +
                    " <li>Giveaways will be released at Metfone showrooms from 16th December 2018 to 21st December 2018." +
                    "" +
                    " </li>" +
                    "" +
                    " <li>The race is on 22nd December 2018 from 4pm to 10pm at Olympic stadium." +
                    "" +
                    " </li>" +
                    "" +
                    " </ul>" +
                    "" +
                    "</div>" +
                    "" +
                    "<p class='classP'>" +
                    "" +
                    "Ticket and entry registration" +
                    "" +
                    "</p>" +
                    "" +
                    "<p style=\"font-size: 17px;\">Types of ticket</p>" +
                    "" +
                    "<div>" +
                    "" +
                    " <ul> " +
                    "" +
                    " <li><strong> VIP 1: </strong>Free entry + 1 T-shirt + concert. <strong>Up to 30,000 pcs</strong>" +
                    "" +
                    "   How to subscribe<strong>: </strong>Download and install Mocha application following the link: <a href=\"http://mocha.com.kh/\" target=\"_blank\">http://mocha.com.kh/</a> <strong>and/or</strong> top-up at least 2$/day by scratch card, eMoney or bank account." +
                    "" +
                    " </li>" +
                    "" +
                    " </ul>" +
                    "" +
                    " " +
                    "" +
                    " <ul> " +
                    "" +
                    " <li> <strong> VIP 2: </strong>Free entry + 1 T-shirt + full equipment (hairpin, glasses, necklace, cheering glow sticks) for the race + concert.<strong> Up to 10,000 pcs</strong>" +
                    "" +
                    "   How to subscribe: Top-up at least 5$/day by scratch card, eMoney or bank account" +
                    "" +
                    " </li>" +
                    "" +
                    " </ul>" +
                    "" +
                    " " +
                    "" +
                    " <ul> " +
                    "" +
                    " <li><strong>VIP 3: </strong>Free entry + 1 T-shirt + (full equipment + 1 glow jacket) for the race + concert. <strong>Up to 1,000 pcs</strong>" +
                    "" +
                    "   How to subscribe: Top-up at least 10$/day by scratch card, eMoney or bank account" +
                    "" +
                    " </li>" +
                    "" +
                    " </ul>" +
                    "" +
                    "</div>" +
                    "" +
                    "<p>How to get the tickets</p>" +
                    "" +
                    "  <ul> " +
                    "" +
                    " <li>When customers download/install Mocha application and top-up, there is a message sent to customers devices in Mocha app or by SMS.</li>" +
                    "" +
                    "  <li>Customer access the link <a href='http://nightrace.metfone.com.kh'> http://nightrace.metfone.com.kh </a>on the message and click on Register button to confirm the participation in the race and get the e-tickets.</li>" +
                    "" +
                    " <li>The quantity of tickets is limited for each type as mentioned above and one customer can only get one ticket</li>" +
                    "" +
                    " " +
                    "" +
                    " </ul>" +
                    "" +
                    " <p>How to entry the race location</p>" +
                    "" +
                    "  <ul> " +
                    "" +
                    " <li>When customers come to the venue - Olympic stadium, scan the received e-tickets with Metfone receptionists to entry the venue.</li>" +
                    "" +
                    " </ul>" +
                    "" +
                    "<p>&nbsp;</p>" +
                    "" +
                    "<p class='classP'>" +
                    "" +
                    "The giveaways </p>" +
                    "" +
                    "<p>How to get the giveaways</p>" +
                    "" +
                    "  <ul> " +
                    "" +
                    " <li>Apply for all participants of the race. The giveaways will be based on the types of ticket.</li>" +
                    "" +
                    "  <li>Customer bring the received QR Codes to Metfone showrooms, scan the e-tickets to receive the giveaways.</li>" +
                    "" +
                    " <li>The giveaways will be released from 16th December to 21st December 2018</li>" +
                    "" +
                    " <li>The ID, passport or other valid documents is required critically when customers go to Metfone showrooms.</li>" +
                    "" +
                    " </ul>" +
                    "" +
                    "<p>Where to get the giveaways</p>" +
                    "" +
                    " <ul>" +
                    "" +
                    "<li><strong>Showroom</strong><strong><em> Metfone PNP05 - TekThla</em></strong></li>" +
                    "" +
                    "<p>Address: #2AB, St Russei, S/Tekthla, K/Sansok, Phnom Penh (Toekthla )</p>" +
                    "" +
                    "<p>Phone: 0236200170/0236200166</p>" +
                    "" +
                    "<li><strong>Showroom</strong><strong><em> Metfone PNP08-Jroy Jongva</em></strong></li>" +
                    "" +
                    "<p>Address: A11&amp;A13, E0, E1, E2, St 70, Sangkat Sras Chork, DounPenh, Phnom Penh</p>" +
                    "" +
                    "<p>Phone: 0236208168 , 0236200169</p>" +
                    "" +
                    "<li><strong>Showroom</strong><strong><em> Metfone PNP11-Toul Svay Prey</em></strong></li>" +
                    "" +
                    "<p>Address: #267, 269, 271E0, 267E1, St Mao Ste Tung, Tul Svay Prey 2 commune, Chamkamon district, Phnom Penh</p>" +
                    "" +
                    "<p>Phone: 0236200174, 0236216168</p>" +
                    "" +
                    "" +
                    "" +
                    "<li><strong>Showroom</strong><strong><em> Metfone PNP28 – Koh Pich</em></strong><p></p>" +
                    "" +
                    "<p>Address: #TONLE BASAK KOH PICH, Phnom Penh</p></li>" +
                    "" +
                    "</ul>" +
                    "" +
                    "<p><strong>&nbsp;</strong></p>" +
                    "" +
                    "<p class='classP'>" +
                    "" +
                    "Miscellaneous" +
                    "" +
                    "</p>" +
                    "" +
                    " <ul> " +
                    "" +
                    " <li>The teasers, promote trailers and other information about the race will be published on the Metfone`s Facebook and Hang Meas Facebook + TV and other media channels.</li>" +
                    "" +
                    "  <li>The race will be live broadcasted on Metfone and Hang Meas Facebook + Television and other media channels.</li>" +
                    "" +
                    " <li>Due to the limit of the tickets and giveaways, Metfone will stop, with prior notice, issuing the tickets and giveaways once it reaches the limited quantity.</li>" +
                    "" +
                    " <li>By entering the race, participants unconditionlly and irrevocably agree to be bound by these terms and conditions. Any breach of the terms and conditions may, at the sole discretion of Metfone, result in forfeiture of the giveaways.</li>" +
                    "" +
                    " <li>Metfone reserves the right to cancel, terminate or suspend the race without any prior notice. For the avoidance of any doubt, any cancellation, termination or suspension by Metfone of the contest does not entitle the participant to any claim or compen sation against Metfone for any or all losses or damages which may be suffered or incurred by the participant as a direct or indirect result of the cancellation, termination or suspension </li>" +
                    "" +
                    " </ul>" +
                    "" +
                    "</div>" +
                    "" +
                    "                " +
                    "" +
                    "            </div>" +
                    "" +
                    "            " +
                    "" +
                    "</body></html>" +
                    "";
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                    Base64.NO_PADDING);
            webviewRule.loadData(encodedHtml, "text/html", "base64");
        }



        return view;
    }
}
