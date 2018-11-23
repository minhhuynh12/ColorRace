package com.example.metfone.colorracemetfone.ui.MainActivityEmploye.fragment;

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
import android.widget.TextView;

import com.example.metfone.colorracemetfone.R;
import com.example.metfone.colorracemetfone.util.SharePreferenceUtils;

public class FAQfragment extends Fragment {
    private TextView tvFAQ;
    private WebView webviewFAQ;
    private ImageView imgFaq;
    private SharePreferenceUtils sharePreferenceUtils;
    public static FAQfragment newInstance() {
        FAQfragment fragmentFirst = new FAQfragment();
        return fragmentFirst;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.fragment_faq, container , false );

        tvFAQ = view.findViewById(R.id.tvFAQ);
        imgFaq = view.findViewById(R.id.imgFaq);
        webviewFAQ = view.findViewById(R.id.webviewFAQ);

        sharePreferenceUtils = new SharePreferenceUtils(getActivity());
        String language = sharePreferenceUtils.getLanguage();

        if ("kh".equals(language)){
            String unencodedHtml =
                    "<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
                            "        " +
                            "</head><body style='background-color:#B40034;'>" +
                            "            <style>" +
                            "                .container p" +
                            "                {" +
                            "                    color:white;" +
                            "                    text-align: left;" +
                            "                }" +
                            "            .container" +
                            "            {" +
                            "                color:white;" +
                            "                font-family:\"Helvetica Neue\",Helvetica,Arial,sans-serif;" +
                            "                text-align: left;" +
                            "            }" +
                            "            .classP" +
                            "            {" +
                            "                text-transform: uppercase;" +
                            "                font-size: 17px;" +
                            "                text-decoration: none;" +
                            "                border-bottom: 1px solid white;" +
                            "                color:#8dda84 !important;" +
                            "            }" +
                            "            li::before {" +
                            "                content: \"• \";" +
                            "                color: #3fc9df;" +
                            "                font-size: 18px;" +
                            "            }" +
                            "            ul {" +
                            "                list-style: none;" +
                            "            }" +
                            "            a:hover, a:focus" +
                            "            {" +
                            "                color:black;" +
                            "            }" +
                            "            a{" +
                            "                color: #8dda84" +
                            "            }" +
                            "</style>" +
                            "                <div class=\"container\" style=\"text-align: justify;\">" +
                            "<p class='classP'>តើ Metfone Night Race មានន័យដូចម្តេច?</p>" +
                            "<p>Night Race គឺជាការរត់ប្រណាំងចំងាយធម្មតា 2.5 គីឡូម៉ែតរបស់មិត្តហ្វូន និងជាបទពិសោធន៍មួយពោពេញទៅដោយភាពសប្បាយរីករាយដល់អ្នកចូលរួមទាំងអស់គ្នាដែលចូលចិត្តស្វែងរកការកំសាន្ត និងភាពរីករាយនៃការរត់ប្រណាំងធម្មតា។</p>" +
                            "<p class='classP'> តើធ្វើដូចម្តេចដើម្បីទទួលបានសំបុត្រ (e-ticket) ចូលរួម Night Race?</p>" +
                            "<p>សំបុត្រ (e-ticket) មាន ៣ប្រភេទដែលអ្នកអាចនឹងទទួលបានដោយការណែនាំ ដូចខាងក្រោម៖</p>" +
                            "<div>" +
                            " <ul> " +
                            " <li><strong> ប្រភេទ VIP1: </strong>ឥតគិតថ្លៃការចូលរួម + អាវយឺត១ + មើលការប្រគុំតន្រ្តី<strong>(សំបុត្រមានប្រហែល 30,000 សំបុត្រ)</strong>" +
                            "  ដើម្បីចុះឈ្មោះ និងទទួលបានសំបុត្រនេះអតិថិជនគ្រាន់តែទាញយក និង ដំឡើងកម្មវិធី Mocha ដោយចូលទៅកាន់គេហទំព័រ <a href=\"http://mocha.com.kh/\"> http://mocha.com.kh/ </a> និង/ឬ បញ្ចូលលុយចាប់ពី ២ដុល្លារ/១ថ្ងៃ តាមរយៈសន្លឹកកាតកោស អ៊ី-ម៉ានី ឬគណនីធនាគារ។" +
                            " </li>" +
                            " </ul>" +
                            " " +
                            " <ul> " +
                            " <li> <strong>ប្រភេទ VIP2: </strong>ឥតគិតថ្លៃការចូលរួម + អាវយឺត១ + សំភារៈពេញមួយឈុត (ដង្កៀបសក់ វ៉ែនតា ខ្សែករ ដងពន្លឺសំរាប់អបអរសាទរ) + មើលការប្រគុំតន្រ្តី។ <strong>(សំបុត្រនេះមានប្រហែល 10,000 សំបុត្រ) </strong> ដើម្បីចុះឈ្មោះ និងទទួលបានសំបុត្រនេះ អតិថិជនតំរូវអោយបញ្ចូលលុយចាប់ពី ៥ដុល្លារ/១ថ្ងៃ តាមរយ:សន្លឹកកាតកោស អ៊ី-ម៉ានី ឬគណនីធនាគារ។" +
                            " </li>" +
                            " </ul>" +
                            " " +
                            " <ul> " +
                            " <li><strong>ប្រភេទ VIP3: </strong>ឥតគិតថ្លៃការចូលរួម + អាវយឺត១ + (សំភារៈពេញមួយឈុត + អាវពន្លឺចំនួន១) + មើលការប្រគុំតន្រ្តី។ សំបុត្រប្រភេទនេះ មានចំនួន <strong> 1,000 </strong> សំបុត្រ។ ដើម្បីចុះឈ្មោះ និងទទួលបានសំបុត្រ គឺតំរូវអតិថិជនបញ្ចូលុយចាប់ពី ១០ដុល្លារ/១ថ្ងៃ តាមរយ:សន្លឹកកាតកោស អ៊ី-ម៉ានី ឬគណនីធនាគារ។" +
                            " </li>" +
                            " </ul>" +
                            "  <ul> " +
                            " <li>បន្ទាប់ពីលោកអ្នកបានបញ្ចូលលុយ ឬដំឡើងកម្មវិធី Mocha បានដោយជោគជ័យ លោកអ្នកនឹងទទួលបានសារអញ្ជើញមួយ ដែលបង្ហាញជាមួយតំណរភ្ជាប់ <a href=\"http://nightrace.metfone.com.kh\">http://nightrace.metfone.com.kh/ticket</a> ដើម្បីទទួលបានសំបុត្រ (e-Ticket) ដោយគ្រាន់តែចុចលើតំណរភ្ជាប់នេះហើយធីក (Ö) ក្នុងប្រអប់ <strong> “ខ្ញុំបានអាននិងយល់ព្រមលើលក្ខខណ្ឌ” </strong> និងចុចលើប៊ូតុង “ទទួលយកសំបុត្រ (e-Ticket)” ដើម្បីបញ្ជាក់ពីការទទួលយកសំបុត្រ (e-ticket)។ សំបុត្រចូលរួមនឹងត្រូវផ្តល់ជូនចាប់ពីថ្ងៃទី <strong> ១៧វិច្ឆិកា ដល់ ១៦ ខែធ្នូឆ្នាំ ២០១៨។</strong></li>" +
                            " </li>" +
                            " </ul>" +
                            "</div>" +
                            "<p class='classP'>" +
                            "តើធ្វើដូចម្តេចដើម្បីទទួលបានសំភារៈ (Kits) ចូលរួម Metfone Night Race?" +
                            "</p>" +
                            "<p>លោកអ្នកគ្រាន់តែយកសំបុត្រ (e-ticket) ទៅកាន់ SHOWROOM របស់ Metfone ដើម្បីស្គេន QR កូដ ដែលមាននៅលើសំបុត្រ (e-ticket) ដើម្បីទទួលយកសំភារៈ  (Kits) សំរាប់ចូលរួម Night Race (ការផ្តល់ជូនសំភារៈ  Kits គឺចាប់ពីថ្ងៃទី ១៦ ដល់ ២១ ខែធ្នូ ឆ្នាំ២០១៨)។</strong>)</p>" +
                            "<p>&nbsp;</p>" +
                            "<p class='classP'>" +
                            "តើកន្លែងណាខ្លះដែលខ្ញុំអាចយកសំបុត្រ (e-ticket) ទៅស្គេន QR កូដ ដើម្បីប្តូរយកសំភារៈ (Kits)?" +
                            "</p>" +
                            "<p>" +
                            "លោកអ្នកអាចយកសំបុត្រ (e-ticket) ទៅស្គេន QR កូដនៅហាងលក់របស់មិត្តហ្វូនណាមួយក្នុងចំណោមហាងលក់ចំនួន ៤ ដូចខាងក្រោម៖" +
                            "</p>" +
                            "<ul>" +
                            "<li><strong>ហាងលក់របស់មិត្តហ្វូន (PNP05) នៅទឹកថ្លា៖</strong></li>" +
                            "<p>អាសយដ្ឋាន៖ #2AB ផ្លូវសហព័ន្ធរុស្សី សង្កាត់ទឹកថ្លា ខណ្ឌសែនសុខ</p>" +
                            "<p>លេខទំនាក់ទំនង៖ 0236200170/0236200166</p>" +
                            "<p>ទីតាំង៖: <a href=\"https://goo.gl/maps/pEgU87oN3uJ2\">https://goo.gl/maps/ocwaEcuZ47E2</a></p>" +
                            "<li><strong>ហាងលក់របស់មិត្តហ្វូន (PNP08) នៅរង្វង់មូលជ្រោយចង្វាខាងលិច៖</strong></li>" +
                            "<p>អាសយដ្ឋាន៖ A11&amp;A13, E0, E1, E2, St 70, Sangkat Sras Chork, DounPenh, Phnom Penh</p>" +
                            "<p>លេខទំនាក់ទំនង៖ 0236208168 , 0236200169</p>" +
                            "<p>ទីតាំង៖: <a href=\"https://goo.gl/maps/YRjmHu4RoXJ2\">https://goo.gl/maps/24joEm3LhK62</a></p>" +
                            "<li><strong>ហាងលក់របស់មិត្តហ្វូន (PNP11) ផ្លូវម៉ៅសេទុង</strong></li>" +
                            "<p>អាសយដ្ឋាន៖ #267, 269, 271E0, 267E1, St Mao Ste Tung, Tul Svay Prey 2 commune, Chamkamon district, Phnom Penh</p>" +
                            "<p>លេខទំនាក់ទំនង៖ 0236200174, 0236216168</p>" +
                            "<p>ទីតាំង៖: <a href=\"https://goo.gl/maps/GAZTAF2rwA72\">https://goo.gl/maps/GAZTAF2rwA72</a></li>" +
                            "<li><strong>ហាងលក់របស់មិត្តហ្វូន (PNP28) កោះពេជ្រ</strong></p>" +
                            "<p>អាសយដ្ឋាន៖ #TONLE BASAK KOH PICH, Phnom Penh</li>" +
                            "<p>ទីតាំង៖: <a href=\"https://goo.gl/maps/ocwaEcuZ47E2\">https://goo.gl/maps/ocwaEcuZ47E2</a></p>" +
                            "</ul>" +
                            "<p><strong>&nbsp;</strong></p>" +
                            "<p class='classP'>" +
                            "តើកម្មវិធី Metfone Night Race នឹងប្រព្រឹត្តិទៅនៅពេលណា?" +
                            "</p>" +
                            "<p>នៅថ្ងៃទី ២២ ខែធ្នូ ឆ្នាំ ២០១៨ ចាប់ពីម៉ោង ៤ល្ងាច ដល់ម៉ោង ១០យប់</p>" +
                            "" +
                            "<p class='classP'>" +
                            "តើអ្នករក្សាទុកសំបុត្រ (e-ticket) QR កូដដោយរបៀបណា?" +
                            "</p>" +
                            "<p>- ព្រីនស្រ្គីនទុកក្នុងទូរស័ព្ទរបស់លោកអ្នក….</p>" +
                            "<p>- ទាញយកកម្មវិធី “Metfone Night Race” ពី Play Store or App Store (iOS)</p>" +
                            "<p>- ចូលទៅកាន់វេបសាយៈ <a href=\"http://nightrace.metfone.com.kh/\">http://nightrace.metfone.com.kh/ticket</a> ម្តងទៀត</p>" +
                            "" +
                            "<p class='classP'>" +
                            "តើកម្មវិធី Metfone Night Race នឹងប្រព្រឹត្តិទៅនៅទីកន្លែងណា ហើយធ្វើដូចម្តេច ដើម្បីអាចចូលទៅកាន់ទីកន្លែងនេះ?" +
                            "</p>" +
                            "<p>នៅពហុកីឡាដ្ឋានអូឡាំពិក។ នៅពេលលោកអ្នកទៅដល់ទីកន្លែងទីលាន បុគ្គលិករបស់មិត្តហ្វូននឹងធ្វើការសេ្គន QR កូដនៅលើសំបុត្រ (e-ticket) របស់អ្នក ហើយអនុញ្ញាតអោយអ្នកចូលទៅកាន់ទីលាននៃកម្មវិធី Metfone Night Race។</p>" +
                            "" +
                            "<p class='classP'>" +
                            "ខ្ញុំបានបញ្ចូលលុយលើលេខមិត្តហ្វូនរបស់ខ្ញុំ តែមិនបានទទួលសារអញ្ជើញដើម្បីទទួលយក សំបុត្រ (e-ticket) តើហេតុអ្វី?" +
                            "</p>" +
                            "<p>- សូមពិនិត្យមើលចំនួនដងបញ្ចូលលុយរបស់អ្នកឡើងវិញ</p>" +
                            "<p>- សូមពិនិត្យមើលចំនួនលុយដែលអ្នកបានបញ្ចូល តើត្រូវទៅតាមលក្ខខណ្ឌហើយឬនៅ</p>" +
                            "" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "តើខ្ញុំអាចអោយអ្នកណាម្នាក់ទៅយក សំភារៈ (Kits) សំរាប់ Metfone Night Race ជំនួសខ្ញុំបានឬទេ?" +
                            "</p>" +
                            "<p>បាទបាន! អ្នកដែលទៅទទួលយកសំភារៈ ត្រូវតែភ្ជាប់មកជាមួយសំបុត្រ (e-ticket) របស់អ្នក ហើយបង្ហាញទៅបុគ្គលិករបស់មិត្តហ្វូនដើម្បីទទួលយកសំភារៈ (Kits)។" +
                            "</p>" +
                            "<p class='classP'>" +
                            "តើខ្ញុំអាចប្តូរទំហំអាវយឺតរបស់ខ្ញុំបានទេ បន្ទាប់ពីខ្ញុំបានចុះឈ្មោះហើយ?" +
                            "</p>" +
                            "<p>លោកអ្នកមិនអាចប្តូរទំហំអាវយឺតរបស់អ្នកបានទេ ប៉ុន្តែនៅពេលដែលអ្នកចូលកម្មវិធី Metfone Night Race ជាមួយមិត្តភ័ក្រ អ្នកអាចប្តូរវាជាមួយមិត្តភ័ក្រអ្នកបាន បើគាត់ យល់ព្រម។" +
                            "</p>" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "បើសិនជាខ្ញុំបានចុះឈ្មោះចូលរួមហើយ តែបែជាមិនអាចចូលរួមវិញ តើខ្ញុំអាចទទួល បានការទូទាត់ត្រឡប់មកវិញដែរឬទេ?" +
                            "</p>" +
                            "<p>ការចុះឈ្មោះហើយ គឺមិនអាចទូទាត់សងត្រឡប់វិញទេ។" +
                            "</p>" +
                            "" +
                            "<p class='classP'>" +
                            "តើខ្ញុំអាចរក្សាទុកទ្រពសម្បត្តិផ្ទាល់ខ្លួននៅកន្លែងណា នៅពេលដែលខ្ញុំចូលរួមកម្មវិធី Metfone Night Race?" +
                            "</p>" +
                            "<p>នៅពេលដែលកម្មវិធី Metfone Night Race កំពុងតែប្រព្រឹត្តិទៅ ពួកយើងមិនមានទូរ ឬតុសំរាប់រក្សាទុកទ្រពសម្បត្តិផ្ទាល់ខ្លួនរបស់អ្នកទេ សូមរក្សាទុកទ្រពសម្បត្តិដែលមានតម្លៃរបស់អ្នកនៅផ្ទះ មុនមកចូលរួមលេងកម្មវិធី Metfone Night Race ដើម្បីជៀសវាងការបាត់បង់ ឬខូចខាតដោយប្រការណាមួយ នៅពេលដែលកម្មវិធីកំពុងតែប្រព្រឹត្តិទៅ។" +
                            "</p>" +
                            "" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "ជំនួយសង្គ្រោះបឋម" +
                            "</p>" +
                            "<p>សេវាសង្គ្រោះបន្ទាន់បឋម នឹងត្រូវបានផ្តល់ជូនសម្រាប់ព្រឹត្តិការណ៍នេះ ក្រុមជំនួយ ចល័តនឹងចាំជួយសង្គ្រោះនៅក្នុងទីលាន ដើម្បីផ្តល់ជំនួយអោយទាន់ពេលវេលា។" +
                            "</p>" +
                            "<p class='classP'>" +
                            "នៅពេលដែលខ្ញុំមានសំណួរហើយ តែមិនអាចឆ្លើយបាន តើខ្ញុំគួទាក់ទងទៅអ្នកណា?" +
                            "</p>" +
                            "<p>សូមផ្ញើសារមកកាន់យើងខ្ញុំតាមរយៈ Facebook: <a href=\"http://www.facebook.com/metfone.closer\">facebook.com/metfone.closer</a> ហើយពួកយើងនឹងធ្វើការឆ្លើយទៅលោកអ្នកភ្លាមៗ ឬលោកអ្នកអាចទំនាក់ទំនងមក កាន់លេខភ្នាក់ងារបំរើសេវាអតិថិជន ២៤ម៉ោង ១៧៧៧។" +
                            "</p>" +
                            "</div>" +
                            "                " +
                            "            </div>" +
                            "            " +
                            "</body></html>";
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                    Base64.NO_PADDING);
            webviewFAQ.loadData(encodedHtml, "text/html", "base64");
        }else {
            String unencodedHtml =
                    "<html lang=\"en\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">" +
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
                            "                <div class=\"container\" style=\"text-align: justify;\">" +
                            "<p class='classP'>What is Night Race mean?</p>" +
                            "<p>Night Race is a Metfone’s regular 2.5km experiencing fun-filled race to all participants who seek beyond entertainments and joyfulness’s of a typical run.</p>" +
                            "<p class='classP'> How to get e-ticket to participate Night Race?</p>" +
                            "<p>There are 3 types of eTicket that you can get by the following instructions:</p>" +
                            "<div>" +
                            " <ul> " +
                            " <li><strong> VIP 1: </strong>Free entry + 1 T-shirt + concert. <strong>Up to 30,000 pcs</strong>" +
                            "   How to </strong>subscribe<strong>: </strong>Download and install Mocha application following the link: <a href=\"http://mocha.com.kh/\">http://mocha.com.kh/</a> <strong>and/or</strong> top-up at least 2$/day by scratch card, eMoney or bank account." +
                            " </li>" +
                            " </ul>" +
                            " " +
                            " <ul> " +
                            " <li> <strong> VIP 2: </strong>Free entry + 1 T-shirt + full equipment (hairpin, glasses, necklace, cheering glow sticks) for the race + concert.<strong> Up to 10,000 pcs</strong>" +
                            "   How to subscribe: Top-up at least 5$/day by scratch card, eMoney or bank account" +
                            " </li>" +
                            " </ul>" +
                            " " +
                            " <ul> " +
                            " <li><strong>VIP 3: </strong>Free entry + 1 T-shirt + (full equipment + 1 glow jacket) for the race + concert. <strong>Up to 1,000 pcs</strong>" +
                            "   How to subscribe: Top-up at least 10$/day by scratch card, eMoney or bank account" +
                            " </li>" +
                            " </ul>" +
                            "  <ul> " +
                            " <li>After you have successful top-up/install Mocha, there is an invitation SMS sent to your devices in Mocha app or by SMS. You just access to the link <a href=\"http://nightrace.metfone.com.kh\">http://nightrace.metfone.com.kh/ticket</a> on the message, tick on box <strong>“I have read and agreed on term and condition”</strong> and click on button <strong>“GET e-TICKET” </strong>to confirm and get the e-Tickets (the tickets will be issued from <strong>17th November 2018 to 16th December 2018</strong>).</li>" +
                            " </li>" +
                            " </ul>" +
                            "</div>" +
                            "<p class='classP'>" +
                            "How to get the Kits to participate Night Race?" +
                            "</p>" +
                            "<p>You just bring e-ticket (QR codes) to scan the QR code at Metfone showroom in Phnom Penh to receive the Kits for participation Night Race (the Kits will be offered<strong> from 16th December 2018 to 21st December 2018</strong>)</p>" +
                            "<p>&nbsp;</p>" +
                            "<p class='classP'>" +
                            "Where to scan e-ticket QR code to redeem the Kits?" +
                            "</p>" +
                            "<p>" +
                            "You can get your e-Ticket to scan QR code for redeem the Kits at 4 showrooms of Metfone in Phnom Penh, from 8AM to 5PM:" +
                            "</p>" +
                            "<ul>" +
                            "<li><strong>Showroom</strong><strong><em> Metfone PNP05 - TekThla</em></strong></li>" +
                            "<p>Address: #2AB, St Russei, S/Tekthla, K/Sansok, Phnom Penh (Toekthla )</p>" +
                            "<p>Phone: 0236200170/0236200166</p>" +
                            "<p>Location: <a href=\"https://goo.gl/maps/pEgU87oN3uJ2\">https://goo.gl/maps/ocwaEcuZ47E2</a></p>" +
                            "<li><strong>Showroom</strong><strong><em> Metfone PNP08-Jroy Jongva</em></strong></li>" +
                            "<p>Address: A11&amp;A13, E0, E1, E2, St 70, Sangkat Sras Chork, DounPenh, Phnom Penh</p>" +
                            "<p>Phone: 0236208168 , 0236200169</p>" +
                            "<p>Location: <a href=\"https://goo.gl/maps/YRjmHu4RoXJ2\">https://goo.gl/maps/24joEm3LhK62</a></p>" +
                            "<li><strong>Showroom</strong><strong><em> Metfone PNP11-Toul Svay Prey</em></strong></li>" +
                            "<p>Address: #267, 269, 271E0, 267E1, St Mao Ste Tung, Tul Svay Prey 2 commune, Chamkamon district, Phnom Penh</p>" +
                            "<p>Phone: 0236200174, 0236216168</p>" +
                            "<p>Location: <a href=\"https://goo.gl/maps/GAZTAF2rwA72\">https://goo.gl/maps/GAZTAF2rwA72</a></li>" +
                            "<li><strong>Showroom</strong><strong><em> Metfone PNP28 – Koh Pich</em></strong></p>" +
                            "<p>Address: #TONLE BASAK KOH PICH, Phnom Penh</li>" +
                            "<p>Location: <a href=\"https://goo.gl/maps/ocwaEcuZ47E2\">https://goo.gl/maps/ocwaEcuZ47E2</a></p>" +
                            "</ul>" +
                            "<p><strong>&nbsp;</strong></p>" +
                            "<p class='classP'>" +
                            "What time the Metfone night race will be held on?" +
                            "</p>" +
                            "<p>On 22<sup>nd</sup> December 2018, from 4PM to 10PM</p>" +
                            "" +
                            "<p class='classP'>" +
                            "How to save e-Ticket QR code?" +
                            "</p>" +
                            "<p>- Take screenshot on your phone</p>" +
                            "<p>- Download application “Metfone Night Race” on PlayStore (android) or App Store (iOS)</p>" +
                            "<p>- Access again website:<a href=\"http://nightrace.metfone.com.kh\">http://nightrace.metfone.com.kh</a></p>" +
                            "" +
                            "<p class='classP'>" +
                            "Where location the Metfone Night Race will held on? And how-to entry the venue?" +
                            "</p>" +
                            "<p>At OLYMPIC STADIUM, when customers come to the venue – Olympic stadium, scan the e-tickets QR code with Metfone receptionists to entry the venue.</p>" +
                            "" +
                            "<p class='classP'>" +
                            "I have top-up balance. Why I didn’t receive invitation SMS of Metfone ?" +
                            "</p>" +
                            "<p>- Check your top-up times</p>" +
                            "<p>- Check your top-up amount in day</p>" +
                            "" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "Can someone else pick up my race Kits for me?" +
                            "</p>" +
                            "<p>Yes, someone else may request your Ki This person will need to present your e-Ticket in order to receive the Kits for you." +
                            "</p>" +
                            "<p class='classP'>" +
                            "Could I change my T-shirt’s size after registered successful?" +
                            "</p>" +
                            "<p>Unfortunately, you can’t change your T-shirt’s size. If you join in with your group, you can change yours with your friends/partner’s." +
                            "</p>" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "If I have registered but would not participated, would I get a refund?" +
                            "</p>" +
                            "<p>The registration fee is not refundable." +
                            "</p>" +
                            "" +
                            "<p class='classP'>" +
                            "Where can I keep my belongings during the event time?" +
                            "</p>" +
                            "<p>There is no counter for keeping the participants’ belongings. Please leave all valuable items at home to avoid unfortunate lost or damage that may occur during the event." +
                            "</p>" +
                            "" +
                            "<p>&nbsp;</p>" +
                            "" +
                            "<p class='classP'>" +
                            "First Aid" +
                            "</p>" +
                            "<p>First Aid and Emergency Service will be provided for the event. Mobile teams move throughout the course to assist you promptly." +
                            "</p>" +
                            "<p class='classP'>" +
                            "What if I have question that isn’t answered here?" +
                            "</p>" +
                            "<p>Please send us a message at <a href=\"http://www.facebook.com/metfone.closer\">facebook.com/metfone.closer</a> and we will get back to you with an answer or contact our Hotline: 1777" +
                            "</p>" +
                            "</div>" +
                            "                " +
                            "            </div>" +
                            "            " +
                            "</body></html>";
            String encodedHtml = Base64.encodeToString(unencodedHtml.getBytes(),
                    Base64.NO_PADDING);
            webviewFAQ.loadData(encodedHtml, "text/html", "base64");
        }

        return view;
    }
}
